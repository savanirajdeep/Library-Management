#!/bin/bash

# Quick deployment commands for OpenShift
# Run these commands before deploying via GUI

echo "🔨 Building Docker images..."

# Build backend image
echo "Building backend image..."
docker build -t library-backend:latest .

# Build frontend image
echo "Building frontend image..."
docker build -t library-frontend:latest ./frontend

echo "✅ Images built successfully!"
echo ""
echo "📤 Next steps:"
echo "1. Get your OpenShift registry URL from the console"
echo "2. Run the following commands (replace with your actual values):"
echo ""
echo "   # Get registry URL and project name"
echo "   REGISTRY=\"default-route-openshift-image-registry.apps.sandbox.openshift.com\""
echo "   PROJECT=\"your-project-name\""
echo ""
echo "   # Tag images"
echo "   docker tag library-backend:latest \$REGISTRY/\$PROJECT/library-backend:latest"
echo "   docker tag library-frontend:latest \$REGISTRY/\$PROJECT/library-frontend:latest"
echo ""
echo "   # Login to OpenShift registry"
echo "   oc login --token=YOUR_TOKEN --server=YOUR_SERVER"
echo ""
echo "   # Push images"
echo "   docker push \$REGISTRY/\$PROJECT/library-backend:latest"
echo "   docker push \$REGISTRY/\$PROJECT/library-frontend:latest"
echo ""
echo "3. Then proceed with GUI deployment using the YAML files provided" 