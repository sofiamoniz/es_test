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
    }
}
