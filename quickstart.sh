#!/bin/bash

# IT Services Portal - Quick Start Script
# This script helps you build, test, and deploy the application

set -e

SCRIPT_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
PROJECT_NAME="IT Services Portal"

echo "================================"
echo "$PROJECT_NAME - Quick Start"
echo "================================"
echo ""

# Check Java version
echo "✓ Checking Java version..."
JAVA_VERSION=$(java -version 2>&1 | grep -oP 'version "\K.*' | cut -d'"' -f1 | cut -d'.' -f1)
if [ "$JAVA_VERSION" -lt 21 ]; then
    echo "✗ Error: Java 21 or later is required. Current version: $JAVA_VERSION"
    exit 1
fi
echo "✓ Java version $JAVA_VERSION detected"
echo ""

# Check Maven
echo "✓ Checking Maven..."
if ! command -v mvn &> /dev/null; then
    echo "✗ Error: Maven is not installed or not in PATH"
    exit 1
fi
echo "✓ Maven found: $(mvn -v | head -1)"
echo ""

# Menu
echo "Select an option:"
echo "1) Build project (compile)"
echo "2) Run tests"
echo "3) Generate code coverage report"
echo "4) Package as WAR"
echo "5) Full build with tests and coverage"
echo "6) Clean project"
echo "7) View coverage report (HTML)"
echo ""
read -p "Enter option (1-7): " option

case $option in
    1)
        echo "Building project..."
        mvn clean compile
        echo "✓ Build complete!"
        ;;
    2)
        echo "Running tests..."
        mvn test
        echo "✓ Tests complete!"
        ;;
    3)
        echo "Generating coverage report..."
        mvn test jacoco:report
        echo "✓ Coverage report generated at target/site/jacoco/index.html"
        ;;
    4)
        echo "Packaging as WAR..."
        mvn clean package -DskipTests
        echo "✓ WAR file created: target/it-services-portal.war"
        echo ""
        echo "To deploy on Tomcat:"
        echo "1. Copy to \$TOMCAT_HOME/webapps/"
        echo "2. Restart Tomcat"
        echo "3. Access at http://localhost:8080/it-services-portal"
        ;;
    5)
        echo "Running full build with tests and coverage..."
        mvn clean verify
        echo "✓ Full build complete!"
        echo "✓ WAR file: target/it-services-portal.war"
        echo "✓ Coverage report: target/site/jacoco/index.html"
        ;;
    6)
        echo "Cleaning project..."
        mvn clean
        echo "✓ Project cleaned!"
        ;;
    7)
        echo "Opening coverage report..."
        if [ -f "target/site/jacoco/index.html" ]; then
            # Try to open with default browser
            if command -v open &> /dev/null; then
                open target/site/jacoco/index.html
            elif command -v xdg-open &> /dev/null; then
                xdg-open target/site/jacoco/index.html
            else
                echo "Please open target/site/jacoco/index.html in your browser"
            fi
            echo "✓ Coverage report opened!"
        else
            echo "✗ Coverage report not found. Run option 3 or 5 first."
        fi
        ;;
    *)
        echo "✗ Invalid option"
        exit 1
        ;;
esac

echo ""
echo "For more information, see README.md"
