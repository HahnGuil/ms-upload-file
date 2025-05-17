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

        stage('Deploy to Localhost') {
            steps {
                echo 'Deploy on localhost'
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