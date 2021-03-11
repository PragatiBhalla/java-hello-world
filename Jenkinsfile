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
        stage('Deploy'){
            steps{
                script{
                    sh ''' wget --user=admin --password=admin123 http://localhost:8090/repository/jenkinstest/org/javahelloworldwebapp/java-hello-world-webapp/1.0.0/java-hello-world-webapp-1.0.0.war
                    docker container stop helloworld
                    docker container rm helloworld
                    /bin/cp java-hello-world-webapp-1.0.0.war ${WORKSPACE}/docker/
                    cd ${WORKSPACE}/docker/
                    docker build -t helloworld:1.0 .
                    docker run -d -p 11080:8080 --name helloworld helloworld:1.0
                    docker container ls
                '''
                    sh 'cd ../kubernetes'
                    try{
                        sh "kubectl apply -f demployment.yml pods.yml service.yml"
                    }catch(error){
                        sh "kubectl create -f demployment.yml pods.yml service.yml"
                    }
                }
            }
        }
    }
}
