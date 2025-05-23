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

        stage('Docker Build') {
            steps {
                echo 'Docker build fase'
                sh 'docker build -t ms-applications .'
            }
        }

        stage('Deploy to Localhost') {
            steps {
                echo 'Deploy on localhost'
                sh 'docker rm -f ms-applications || true'
                sh 'docker run -d --name ms-applications --network mongo-net -p 8085:8085 ms-applications'
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