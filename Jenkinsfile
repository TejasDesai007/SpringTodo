pipeline {
    agent any

    environment {
        REMOTE_HOST = 'ec2-user@13.201.76.225'   // replace this
        DEPLOY_DIR = '/home/ec2-user/springtodo'
        JAR_NAME = 'springtodo.jar'
        SSH_CRED = 'cc-lab8' // Jenkins credential ID for your PEM key
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build with Gradle') {
            steps {
                sh 'chmod +x gradlew'
                sh './gradlew clean build'
            }
        }

        stage('Deploy to EC2') {
            steps {
                sshagent (credentials: [env.SSH_CRED]) {
                    sh """
                        ssh -o StrictHostKeyChecking=no ${REMOTE_HOST} 'mkdir -p ${DEPLOY_DIR}'
                        scp -o StrictHostKeyChecking=no build/libs/*.jar ${REMOTE_HOST}:${DEPLOY_DIR}/${JAR_NAME}
                        ssh ${REMOTE_HOST} 'pkill -f ${JAR_NAME} || true'
                        ssh ${REMOTE_HOST} 'nohup java -jar ${DEPLOY_DIR}/${JAR_NAME} > ${DEPLOY_DIR}/app.log 2>&1 &'
                    """
                }
            }
        }
    }
}
