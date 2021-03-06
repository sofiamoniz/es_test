def remote = [:]
remote.host = "192.168.160.87"
remote.name = "runtime"

pipeline {
    agent any
    
    environment {
        spring_api = ""
        customImage = ""
    }
    
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
        
        stage('Build images'){
            steps{
                script{
                      docker.withRegistry('http://192.168.160.48:5000') {
                            spring_api = docker.build("esp50/followsky", "./followSky")
                    }
                    docker.withRegistry('http://192.168.160.48:5000') {
                            customImage = docker.build("esp50/webapp", "./webapp")
                    }
                }
            }
        }
        
        stage('Publish images'){
            steps{
                script{
                      docker.withRegistry('http://192.168.160.48:5000') {
                            // Push the container to the custom Registry 
                            spring_api.push()

                    }
                    docker.withRegistry('http://192.168.160.48:5000') {
                            // Push the container to the custom Registry 
                            customImage.push()

                    }
                }
                sh 'docker rmi esp50/followsky'
                sh 'docker rmi esp50/webapp'
            }
        }
        
        stage('Runtime Deployment') { 
            steps {
                 withCredentials([usernamePassword(credentialsId: 'esp50_ssh_credentials', usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]) {
                    
                    script {
                      remote.user = USERNAME
                      remote.password = PASSWORD
                      remote.allowAnyHosts = true
                        
                    }
                    
                    sshCommand remote: remote, command: "docker stop esp50-webapp"
                    sshCommand remote: remote, command: "docker rm esp50-webapp"
                     sshCommand remote: remote, command: "docker rmi 192.168.160.48:5000/esp50/webapp"
                     //sshCommand remote: remote, command: "docker pull 192.168.160.48:5000/esp50/webapp"
                     //sshCommand remote: remote, command: "docker create -p 50003:3000 --name esp50-webapp 192.168.160.48:5000/esp50/webapp"
                     //sshCommand remote: remote, command: "docker start esp50-webapp"
                     
                    sshCommand remote: remote, command: "docker stop esp50-followsky"
                    sshCommand remote: remote, command: "docker rm esp50-followsky"
                     sshCommand remote: remote, command: "docker rmi 192.168.160.48:5000/esp50/followsky"
                     //sshCommand remote: remote, command: "docker pull 192.168.160.48:5000/esp50/followsky"
                     //sshCommand remote: remote, command: "docker create -p 50004:8080 --name esp50-followsky 192.168.160.48:5000/esp50/followsky"
                     //sshCommand remote: remote, command: "docker start esp50-followsky"
                }
                  
                
            }
        }

        
        
    }
}
