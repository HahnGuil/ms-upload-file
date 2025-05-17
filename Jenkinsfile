pipeline {
    agent any

    tools {
        jdk 'jdk-21'
        maven 'maven-3'
    }

    stages {
        stage('Checkout') {
            steps {
            echo 'Checkout fase'
                checkout scm
            }
        }

        stage('Build') {
            steps {
            echo 'Build fase'
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('Test') {
            steps {
            echo 'test fase'
                sh 'mvn test'
            }
            post {
                always {
                    junit '**/target/surefire-reports/*.xml'
                }
            }
        }

        stage('Deploy to Nexus') {
            echo 'Nexus fase'
            steps {
                sh 'mvn deploy -DskipTests'
            }
        }

        stage('Deploy to Localhost') {
        echo 'Deploy on localhost'
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