# SSB_PROJECT
Welcome to Our Project

## Spring Boot Application with MySQL, OAuth2, and File Upload

### Technologies Used
- Spring Boot 2.7.3
- MySQL Database
- Spring Security with OAuth2 (Google)
- Thymeleaf
- Cloudinary (File Storage)
- Gmail SMTP
- MyBatis
- PDF Generation (iText, OpenPDF)

---

## Deployment Guide to Render

### Step 1: Prerequisites
1. Create account at https://render.com (Free)
2. Create GitHub account if you don't have one
3. Have your credentials ready:
   - Gmail username & app password
   - Cloudinary API keys
   - Google OAuth2 credentials

### Step 2: Push Code to GitHub
```bash
# Initialize git (if not already done)
git init
git add .
git commit -m "Initial commit for deployment"

# Create repository on GitHub, then:
git remote add origin https://github.com/YOUR_USERNAME/YOUR_REPO_NAME.git
git branch -M main
git push -u origin main
```

### Step 3: Create MySQL Database on Render
1. Go to https://dashboard.render.com
2. Click "New +" and select "MySQL"
3. Fill in:
   - Name: `ssb-database`
   - Database: `ssb`
   - User: `ssb_user` (or any name)
4. Click "Create Database"
5. Wait for database to be created
6. Copy the "Internal Database URL" - you'll need this

### Step 4: Deploy Spring Boot App on Render
1. Click "New +" and select "Web Service"
2. Connect your GitHub repository
3. Fill in:
   - Name: `ssb-project`
   - Environment: `Java`
   - Build Command: `./mvnw clean install -DskipTests`
   - Start Command: `java -jar target/SSB_FINAL_PROJECT-0.0.1-SNAPSHOT.jar`
4. Select Free plan
5. Click "Advanced" to add environment variables

### Step 5: Add Environment Variables on Render
Add these environment variables (use your actual values):

| Key | Value | Description |
|-----|-------|-------------|
| `DATABASE_URL` | (From Step 3 - Internal Database URL) | MySQL connection URL from Render |
| `DB_USERNAME` | (From Render MySQL) | Database username from Render |
| `DB_PASSWORD` | (From Render MySQL) | Database password from Render |
| `GMAIL_USERNAME` | Your Gmail address | Gmail for sending emails |
| `GMAIL_PASSWORD` | Your Gmail app password | Gmail app-specific password |
| `CLOUDINARY_CLOUD_NAME` | Your Cloudinary name | From Cloudinary dashboard |
| `CLOUDINARY_API_KEY` | Your Cloudinary key | From Cloudinary dashboard |
| `CLOUDINARY_API_SECRET` | Your Cloudinary secret | From Cloudinary dashboard |
| `GOOGLE_CLIENT_ID` | Your Google OAuth Client ID | From Google Cloud Console |
| `GOOGLE_CLIENT_SECRET` | Your Google OAuth Client Secret | From Google Cloud Console |

**Note:** Get your actual credentials from:
- MySQL: Render Database dashboard
- Gmail: Google Account > Security > App Passwords
- Cloudinary: https://cloudinary.com/console
- Google OAuth: https://console.cloud.google.com/apis/credentials

### Step 6: Update Google OAuth2 Redirect URI
1. Go to https://console.cloud.google.com/apis/credentials
2. Edit your OAuth 2.0 Client
3. Add Authorized Redirect URI:
   - `https://your-app-name.onrender.com/login/oauth2/code/google`
4. Save

### Step 7: Deploy
1. Click "Create Web Service"
2. Wait 5-10 minutes for deployment
3. Check logs for any errors
4. Visit your app at: `https://your-app-name.onrender.com`

---

## Running Locally

### Prerequisites
- Java 17
- MySQL Server
- Maven

### Steps
1. Create MySQL database named `ssb`
2. Update `application.properties` with your local MySQL credentials
3. Run:
```bash
./mvnw spring-boot:run
```
4. Access at: http://localhost:8080

---

## Important Notes
- Free tier on Render spins down after inactivity - first request may be slow
- Database has limited storage on free tier
- Update OAuth redirect URIs when changing domain
- Never commit sensitive credentials to GitHub

---

Created: 2022/9/16
Last Updated: 2025
