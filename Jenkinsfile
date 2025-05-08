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

        stage('Deploy (Optional)') {
            when {
                expression { false } // Set to true if deploying
            }
            steps {
                echo "Deploying to Kubernetes or another environment"
            }
        }
    }
}
