pipeline {
    agent any

    tools {
        jdk 'jdk-21'
        maven 'maven-3'
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('Test') {
            steps {
                sh 'mvn test'
            }
            post {
                always {
                    junit '**/target/surefire-reports/*.xml'
                }
            }
        }

        stage('Deploy to Nexus') {
            steps {
                sh 'mvn deploy -DskipTests'
            }
        }

        stage('Deploy to Localhost') {
            steps {
                // Copia o novo JAR para dentro do container
                sh 'docker cp target/*.jar ms-applications:/app/app.jar'

                // Mata o processo antigo do Java
                sh 'docker exec ms-applications pkill -f "java -jar" || true'

                // Inicia a aplicação novamente em background
                sh 'docker exec -d ms-applications java -jar /app/app.jar'
            }
        }
    }

    post {
        success {
            echo 'Deployment to localhost successful!'
        }
        failure {
            echo 'Deployment failed!'
        }
    }
}