def remote = [:]
remote.host = "192.168.160.87"
remote.name = "runtime"

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
                sh 'mvn deploy -f followSky/pom.xml -s followSky/settings.xml' 
            }
        }
        
        
        stage("publish-image"){
            steps{
                script{
                      docker.withRegistry('http://192.168.160.48:5000') {
                            def spring_api = docker.build("esp50/followsky", "./followSky")

                            // Push the container to the custom Registry 
                            spring_api.push()

                    }
                    docker.withRegistry('http://192.168.160.48:5000') {
                            def customImage = docker.build("esp50/webapp", "./webapp")

                            // Push the container to the custom Registry 
                            customImage.push()

                    }
                }
            }
        }
        
        stage('Runtime Deployment') { 
            steps {
                 withCredentials([usernamePassword(credentialsId: 'esp50_ssh_credentials', usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]) {
                    echo "here..."
                    echo "$remote.host"
                    
                    script {
                      remote.user = USERNAME
                      remote.password = PASSWORD
                      remote.allowAnyHosts = true
                        
                    }
                    echo "$remote.user"
                    
                    sshCommand remote: remote, command: "docker stop esp50-webapp"
                    sshCommand remote: remote, command: "docker rm esp50-webapp"
                     sshCommand remote: remote, command: "docker rmi 192.168.160.48:5000/esp50/webapp"
                     sshCommand remote: remote, command: "docker pull 192.168.160.48:5000/esp50/webapp"
                     sshCommand remote: remote, command: "docker create -p 50003:3000 --name esp50-webapp 192.168.160.48:5000/esp50/webapp"
                     sshCommand remote: remote, command: "docker start esp50-webapp"
                     
                    sshCommand remote: remote, command: "docker stop esp50-followsky"
                    sshCommand remote: remote, command: "docker rm esp50-followsky"
                     sshCommand remote: remote, command: "docker rmi 192.168.160.48:5000/esp50/followsky"
                     sshCommand remote: remote, command: "docker pull 192.168.160.48:5000/esp50/followsky"
                     sshCommand remote: remote, command: "docker create -p 50004:8080 --name esp50-followsky 192.168.160.48:5000/esp50/followsky"
                     sshCommand remote: remote, command: "docker start esp50-followsky"
                }
                  
                
            }
        }

        
        
    }
}
