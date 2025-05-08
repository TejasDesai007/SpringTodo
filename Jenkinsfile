pipeline {
    agent any

    environment {
        DOCKER_IMAGE = 'tejas793/gradle-spring-demo:latest'
        DOCKER_CREDENTIALS_ID = 'docker-hub-creds'
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build') {
            steps {
                sh 'chmod +x ./gradlew'
                sh './gradlew clean build'
            }
        }

        stage('Docker Build') {
            steps {
                script {
                    docker.build(DOCKER_IMAGE)
                }
            }
        }

        stage('Push Docker Image') {
            steps {
                script {
                    withDockerRegistry([credentialsId: DOCKER_CREDENTIALS_ID, url: '']) {
                        docker.image(DOCKER_IMAGE).push()
                    }
                }
            }
        }

        stage('Deploy') {
            when {
                expression { true } // Change to a condition if needed
            }
            steps {
                sh '''
                    docker pull ${DOCKER_IMAGE}
                    docker stop springboot-app || true
                    docker rm springboot-app || true
                    docker run -d -p 8081:8080 --name springboot-app ${DOCKER_IMAGE}
                '''
            }
        }
    }
}
