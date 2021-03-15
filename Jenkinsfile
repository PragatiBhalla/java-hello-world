#!/usr/bin/env groovy
def pom
pipeline{
    agent any
    stages{
        stage('checkout repo'){
            steps{
                git(url: "https://github.com/PragatiBhalla/java-hello-world.git",branch: "main")
            }
        }
        stage('Build'){
            steps{
                sh script: 'mvn clean install'
            }
        }
        stage('Code Analysis'){
            steps{
                sh script: 'mvn clean install sonar:sonar'
            }
        }
        stage('Publish'){
            steps{
                script{
                pom = readMavenPom file: 'pom.xml'
                nexusArtifactUploader artifacts: [
                    [
                        artifactId: "${pom.artifactId}",
                        classifier: "",
                        file: "target/${pom.artifactId}-${pom.version}.${pom.packaging}",
                        type: "${pom.packaging}"
                    ]
                ],
                credentialsId: "nexus",
                groupId: "${pom.groupId}",
                nexusUrl: "localhost:8090",
                nexusVersion: "nexus3",
                protocol: "http",
                repository: "jenkinstest",
                version: "${pom.version}"
                }
            }
        }
        stage('Deploy using Docker'){
            steps{
                script{
                    sh(returnStatus: true, script: "wget -N --user=admin --password=admin123 http://localhost:8090/repository/jenkinstest/org/javahelloworldwebapp/${pom.artifactId}/${pom.version}/${pom.artifactId}-${pom.version}.${pom.packaging} && /bin/cp ${pom.artifactId}-${pom.version}.${pom.packaging} ${WORKSPACE}/docker/")
                    sh 'docker container ls'
                    try{
                        sh ''' docker container stop helloworld
                        docker container rm helloworld
                        '''
                        sh(returnStatus: true, script: "cd ${WORKSPACE}/docker/ && docker build -t helloworld:${pom.version} . && docker run -d -p 11080:8080 --env version=${pom.version} --name helloworld helloworld:${pom.version}")
                    }catch(error){
                        sh(returnStatus: true, script: "cd ${WORKSPACE}/docker/ && docker build -t helloworld:${pom.version} . && docker run -d -p 11080:8080 --env version=${pom.version} --name helloworld helloworld:${pom.version}")
                    }
                }
            }
        }
        stage('Deploy using K8s'){
            steps{
                script{
                    try{
                        sh '''cd ${WORKSPACE}/kubernetes/
                        kubectl apply -f .
                        '''
                    }catch(error){
                        sh '''cd ${WORKSPACE}/kubernetes/
                        kubectl create -f .
                        '''
                    }
                }
            }
        }
    }
}
