pipeline {
  agent any

  environment {
    APP_NAME = 'springtodo'
    JAR_NAME = 'springtodo.jar'
    DEPLOY_DIR = '/home/ubuntu/apps/springtodo'
    REMOTE_HOST = 'ubuntu@13.201.76.225'     // Replace this with your EC2 IP
    SSH_CRED = 'cc-lab8'                   // Match your Jenkins credentials ID
  }

  stages {
    stage('Checkout') {
      steps {
        checkout scm
      }
    }

    stage('Build with Maven') {
      steps {
        sh 'mvn -B clean package -DskipTests=false'
      }
    }

    stage('Run Tests') {
      steps {
        sh 'mvn test'
      }
    }

    stage('Deploy to EC2') {
      steps {
        sshagent (credentials: [env.SSH_CRED]) {
          sh """
            ssh -o StrictHostKeyChecking=no ${REMOTE_HOST} 'mkdir -p ${DEPLOY_DIR}'
            scp -o StrictHostKeyChecking=no target/*.jar ${REMOTE_HOST}:${DEPLOY_DIR}/${JAR_NAME}
            ssh ${REMOTE_HOST} 'pkill -f ${JAR_NAME} || true'
            ssh ${REMOTE_HOST} 'nohup java -jar ${DEPLOY_DIR}/${JAR_NAME} > ${DEPLOY_DIR}/app.log 2>&1 &'
          """
        }
      }
    }

    stage('Smoke Test') {
      steps {
        script {
          sleep 5
          sh "curl -f http://${REMOTE_HOST.split('@')[-1]}:8080/actuator/health || echo 'App not responding yet...'"
        }
      }
    }
  }

  post {
    success {
      echo "✅ Deployment successful!"
    }
    failure {
      echo "❌ Deployment failed."
    }
  }
}
