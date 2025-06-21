# OpenShift Deployment Checklist

## ✅ Pre-deployment
- [ ] Docker images built locally
- [ ] Images pushed to OpenShift registry
- [ ] OpenShift CLI (oc) installed and logged in
- [ ] Project/namespace created in OpenShift

## 🔐 Secrets
- [ ] `mysql-secret` created with username/password
- [ ] `jwt-secret` created with JWT secret key

## 🗄️ Database
- [ ] MySQL deployment created
- [ ] MySQL service created
- [ ] MySQL PVC created (1Gi)
- [ ] MySQL pod running

## 🔧 Backend
- [ ] Backend deployment created
- [ ] Backend service created
- [ ] Backend route created
- [ ] Environment variables set:
  - [ ] `SPRING_PROFILES_ACTIVE=openshift`
  - [ ] `DATABASE_URL` pointing to MySQL service
  - [ ] Database credentials from secrets
  - [ ] JWT secret from secret
  - [ ] `CORS_ALLOWED_ORIGINS=*`
- [ ] Backend pod running

## 🎨 Frontend
- [ ] Frontend deployment created
- [ ] Frontend service created
- [ ] Frontend route created
- [ ] Environment variables set:
  - [ ] `REACT_APP_API_URL` pointing to backend route
- [ ] Frontend pod running

## 🌐 Networking
- [ ] All services created and accessible
- [ ] All routes created and accessible
- [ ] TLS termination configured

## 🧪 Testing
- [ ] Frontend URL accessible
- [ ] Backend API accessible
- [ ] Swagger UI accessible
- [ ] Login functionality working
- [ ] Database operations working

## 📊 Monitoring
- [ ] Pod logs checked
- [ ] Resource usage monitored
- [ ] Health checks configured

## 🔑 Default Credentials
- Admin: admin@library.com / admin123
- User: user@library.com / user123 