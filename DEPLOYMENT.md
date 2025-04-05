# TestGenie Deployment Guide

This guide explains how to deploy the TestGenie application on Vercel (frontend) and Render (backend).

## Backend Deployment (Render)

1. Create a Render account at [render.com](https://render.com)
2. Create a new Web Service
3. Connect your GitHub repository
4. Configure the service:
   - **Name**: testgenie-backend
   - **Environment**: Java
   - **Build Command**: `./mvnw clean package -DskipTests`
   - **Start Command**: `java -jar target/testgenie-0.0.1-SNAPSHOT.jar`
   - **Environment Variables**:
     - `SPRING_PROFILES_ACTIVE`: prod
     - `PORT`: 8080
5. Deploy the service

## Frontend Deployment (Vercel)

1. Create a Vercel account at [vercel.com](https://vercel.com)
2. Import your GitHub repository
3. Configure the project:
   - **Framework Preset**: Create React App
   - **Build Command**: `npm run build`
   - **Output Directory**: build
   - **Environment Variables**:
     - `REACT_APP_API_URL`: https://testgenie-backend.onrender.com
4. Deploy the project

## Verifying the Deployment

1. Check that the backend is running at https://testgenie-backend.onrender.com
2. Check that the frontend is running at https://testgenie.vercel.app
3. Test the application by generating test cases

## Troubleshooting

- If the backend is not accessible, check the Render logs
- If the frontend is not connecting to the backend, verify the CORS configuration
- If the application is not working as expected, check the browser console for errors 