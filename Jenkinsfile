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
        stage("publish-image"){
          docker.withRegistry('http://192.168.160.48:5000') {

                def customImage = docker.build("esp50/webapp", "./webapp")

                /* Push the container to the custom Registry */
                customImage.push()
            }
        }
    }
}
