pipeline {
    agent any
    tools {
        maven 'maven36'
    }
    stages {
        stage ('Initialize') {
            steps {
                dir('followSky'){  
                    sh '''
                        echo "PATH = ${PATH}"
                        echo "M2_HOME = ${M2_HOME}"
                    '''
                }
            }
        }

        stage ('Build') {
            steps {
                dir('followSky'){  
                    sh 'mvn -Dmaven.test.failure.ignore=true install' 
                }
            }
            post {
                success {
                    dir('followSky'){  
                        junit 'target/surefire-reports/**/*.xml' 
                    }
                }
            }
        }
        stage ('Deploy') {
            steps{
                dir('followSky'){  
                        sh 'mvn deploy -f ./pom.xml -s ./settings.xml' 
                }
            }
        }
        stage("publish-image"){
            steps{
                script{
                      docker.withRegistry('http://192.168.160.48:5000') {
                            def customImage = docker.build("esp50/webapp", "./webapp")

                            /* Push the container to the custom Registry */
                            customImage.push()

                    }
                }
            }
        }
        /*
        stage('Runtime Deployment') { 
            steps {
                sshagent(credentials: ['esp50_ssh_credentials']){
                    sh "ssh -o 'StrictHostKeyChecking=no' -l esp50 192.168.160.87 docker stop esp50-webapp"
                    sh "ssh -o 'StrictHostKeyChecking=no' -l esp50 192.168.160.87 docker rm esp50-webapp"
                    sh "ssh -o 'StrictHostKeyChecking=no' -l esp50 192.168.160.87 docker rmi 192.168.160.48:5000/esp50/webapp"
                    sh "ssh -o 'StrictHostKeyChecking=no' -l esp50 192.168.160.87 docker pull 192.168.160.48:5000/esp50/webapp"
                    sh "ssh -o 'StrictHostKeyChecking=no' -l esp50 192.168.160.87 docker create -p 50003:50003 --name esp50-webapp 192.168.160.48:5000/esp50/webapp"
                    sh "ssh -o 'StrictHostKeyChecking=no' -l esp50 192.168.160.87 docker start esp50-webapp"
                }
            }
        }
        */
    }
}
