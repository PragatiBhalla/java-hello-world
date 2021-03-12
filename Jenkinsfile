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
        stage('Publish'){
            steps{
                script{
                def pom = readMavenPom file: 'pom.xml'
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
                    sh ''' wget --user=admin --password=admin123 http://localhost:8090/repository/jenkinstest/org/javahelloworldwebapp/java-hello-world-webapp/1.0.0/java-hello-world-webapp-1.0.0.war
                    /bin/cp java-hello-world-webapp-1.0.0.war ${WORKSPACE}/docker/
                    docker container ls
                    '''
                    try{
                        sh ''' docker container stop helloworld
                        docker container rm helloworld
                        cd ${WORKSPACE}/docker/
                        docker build -t helloworld:1.0 .
                        docker run -d -p 11080:8080 --name helloworld helloworld:1.0
                        '''
                    }catch(error){
                        sh '''cd ${WORKSPACE}/docker/
                        docker build -t helloworld:1.0 .
                        docker run -d -p 11080:8080 --name helloworld helloworld:1.0
                        '''
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
