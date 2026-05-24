# IT Services Portal - Deployment Checklist

## Pre-Deployment Verification ✓

### System Requirements
- [ ] Java 21 or later installed
- [ ] Maven 3.6+ installed
- [ ] Tomcat 10.1+ available
- [ ] 100MB disk space available
- [ ] Port 8080 available (or configured alternate)

### Build Verification
```bash
# Run this before deployment
mvn clean verify
```

- [ ] Build successful (BUILD SUCCESS message)
- [ ] All 38 tests passing
- [ ] Code coverage meets 80% threshold
- [ ] No compilation errors
- [ ] WAR file created: target/it-services-portal.war

---

## Pre-Deployment Checklist

### 1. Code Verification
- [ ] All source files compiled
- [ ] No merge conflicts
- [ ] All tests passing
- [ ] Code coverage reports generated
- [ ] Static analysis (CheckStyle) passing

```bash
mvn clean compile
mvn test
mvn checkstyle:check
```

### 2. Documentation Check
- [ ] README.md exists and updated
- [ ] COMPLETION_SUMMARY.md available
- [ ] This checklist reviewed
- [ ] Inline code documentation complete

### 3. Configuration Review
- [ ] `pom.xml` configured correctly
- [ ] `web.xml` properly set up
- [ ] `log4j2.xml` configured
- [ ] Java version set to 21
- [ ] All dependencies resolved

### 4. WAR Package Verification
- [ ] WAR file exists: `target/it-services-portal.war`
- [ ] WAR file size is ~5.4 MB (normal range)
- [ ] WAR file readable and not corrupted

```bash
ls -lh target/it-services-portal.war
jar tf target/it-services-portal.war | head
```

### 5. Data Directory Setup
- [ ] Created `data/` directory (auto-created on first run)
- [ ] Write permissions set: `chmod 755 data/`
- [ ] Logs directory created: `logs/`

---

## Deployment Steps

### Step 1: Prepare Tomcat
```bash
# Stop Tomcat (if running)
$TOMCAT_HOME/bin/catalina.sh stop

# Wait for shutdown (30 seconds)
sleep 30

# Clear webapps (backup first if needed)
# rm $TOMCAT_HOME/webapps/it-services-portal* 2>/dev/null || true
```

### Step 2: Deploy WAR File
```bash
# Copy WAR to webapps
cp target/it-services-portal.war $TOMCAT_HOME/webapps/

# Verify copy
ls -la $TOMCAT_HOME/webapps/it-services-portal.war
```

### Step 3: Start Tomcat
```bash
# Start Tomcat
$TOMCAT_HOME/bin/catalina.sh start

# Wait for startup (20 seconds)
sleep 20

# Check startup logs
tail -f $TOMCAT_HOME/logs/catalina.out
```

### Step 4: Verify Deployment
```bash
# Test application availability
curl http://localhost:8080/it-services-portal

# Should return 302 redirect to login page
# Expected response: HTTP/1.1 302
```

### Step 5: Browser Testing
- [ ] Open: http://localhost:8080/it-services-portal
- [ ] Should redirect to: http://localhost:8080/it-services-portal/login.jsp
- [ ] Login page loads successfully
- [ ] CSS styles applied correctly

---

## Post-Deployment Testing

### User Registration Test
1. [ ] Click "Register here"
2. [ ] Enter test credentials:
   - Email: testuser@example.com
   - Full Name: Test User
   - Password: TestPassword123
   - Confirm: TestPassword123
3. [ ] Click Register
4. [ ] Verify success message
5. [ ] Redirected to login page

### User Login Test
1. [ ] Enter test credentials
2. [ ] Click Login
3. [ ] Dashboard loads successfully
4. [ ] User greeting displayed
5. [ ] All tabs accessible

### Dashboard Navigation Test
1. [ ] Company Info Tab
   - [ ] Company description visible
   - [ ] Statistics displayed
   - [ ] Contact information shown
2. [ ] Services Tab
   - [ ] All 5 services displayed
   - [ ] Service descriptions visible
   - [ ] Product count shown
3. [ ] Products Tab
   - [ ] 15 products displayed
   - [ ] Product images load
   - [ ] Pricing visible
   - [ ] External links functional

### Logout Test
1. [ ] Click Logout button
2. [ ] Redirected to login page
3. [ ] Session properly terminated
4. [ ] Cannot access dashboard without login

---

## Performance Testing

### Page Load Times
```bash
# Using curl
time curl -I http://localhost:8080/it-services-portal/login.jsp
time curl -I http://localhost:8080/it-services-portal/dashboard.jsp
```

- [ ] Login page: < 1 second
- [ ] Dashboard: < 2 seconds
- [ ] No 500 errors in logs

### Memory Usage
```bash
# Check Tomcat memory
jps -l | grep Tomcat
jmap -heap <PID> | grep -E "Max|Used"
```

- [ ] Memory usage stable
- [ ] No memory leaks
- [ ] GC pauses minimal

### Concurrent Users Test
```bash
# Apache Bench (if installed)
ab -n 100 -c 10 http://localhost:8080/it-services-portal/login.jsp
```

- [ ] Handle concurrent requests
- [ ] No connection errors
- [ ] Response time acceptable

---

## Security Verification

### Authentication Security
- [ ] Wrong password rejected
- [ ] Invalid email rejected
- [ ] Empty fields rejected
- [ ] Session invalidated on logout
- [ ] Cannot access protected pages without login

### Password Security
- [ ] Passwords hashed (verify in data/users.json)
- [ ] Hashes start with "$2a$12$" (BCrypt)
- [ ] No plaintext passwords stored
- [ ] Weak passwords rejected

### Session Security
- [ ] Session cookies HTTP-only
- [ ] Session timeout working
- [ ] Multiple logins possible with different accounts
- [ ] Logout properly invalidates session

---

## Logging & Monitoring

### Check Logs
```bash
# Tomcat logs
tail -f $TOMCAT_HOME/logs/catalina.out
tail -f $TOMCAT_HOME/logs/localhost.*.log

# Application logs
tail -f logs/app.log
```

- [ ] No ERROR level logs
- [ ] No WARN level logs (except expected)
- [ ] INFO logs show user actions
- [ ] No stack traces in logs

### Log Contents
- [ ] User registration logged
- [ ] User login logged
- [ ] User logout logged
- [ ] Failed auth attempts logged
- [ ] File operations logged

---

## Rollback Plan

If deployment fails:

### Step 1: Stop Tomcat
```bash
$TOMCAT_HOME/bin/catalina.sh stop
```

### Step 2: Remove Failed Deployment
```bash
rm -rf $TOMCAT_HOME/webapps/it-services-portal*
```

### Step 3: Restore Previous Version (if applicable)
```bash
cp backup/it-services-portal.war $TOMCAT_HOME/webapps/
```

### Step 4: Restart Tomcat
```bash
$TOMCAT_HOME/bin/catalina.sh start
```

### Step 5: Verify Rollback
```bash
curl http://localhost:8080/it-services-portal
```

---

## Maintenance Tasks

### Regular Checks
- [ ] Weekly: Check application logs
- [ ] Weekly: Monitor disk space (data/ directory)
- [ ] Monthly: Review user statistics
- [ ] Monthly: Check Tomcat memory usage
- [ ] Quarterly: Update dependencies

### Backup Procedures
```bash
# Backup user data
cp -r data/ data.backup.$(date +%Y%m%d%H%M%S)/

# Backup WAR file
cp target/it-services-portal.war backups/
```

### Monitoring
```bash
# Monitor disk usage
df -h $TOMCAT_HOME/

# Monitor logs size
du -sh logs/

# Monitor user data file
ls -lh data/users.json
```

---

## Troubleshooting

### Issue: Port Already in Use
**Solution**: 
```bash
# Find process on port 8080
lsof -i :8080

# Kill process or change Tomcat port
```

### Issue: WAR Not Deployed
**Solution**:
```bash
# Check Tomcat logs
tail -100 $TOMCAT_HOME/logs/catalina.out

# Ensure write permissions
ls -la $TOMCAT_HOME/webapps/

# Redeploy manually
rm -rf $TOMCAT_HOME/webapps/it-services-portal*
cp target/it-services-portal.war $TOMCAT_HOME/webapps/
```

### Issue: Users Can't Login
**Solution**:
```bash
# Check users.json exists
ls -la data/users.json

# Check file permissions
chmod 644 data/users.json

# Verify JSON format
cat data/users.json | python -m json.tool
```

### Issue: High Memory Usage
**Solution**:
```bash
# Increase Tomcat heap
export CATALINA_OPTS="-Xmx1024m -Xms512m"
$TOMCAT_HOME/bin/catalina.sh start

# Restart Tomcat
$TOMCAT_HOME/bin/catalina.sh stop
$TOMCAT_HOME/bin/catalina.sh start
```

---

## Post-Deployment Sign-Off

- [ ] Code review completed
- [ ] Build verified successful
- [ ] All tests passing (38/38)
- [ ] Code coverage meets threshold (80%+)
- [ ] WAR deployed to Tomcat
- [ ] Application accessible
- [ ] User registration working
- [ ] User login working
- [ ] Dashboard displaying correctly
- [ ] All tabs functional
- [ ] Logout working
- [ ] Logs show no errors
- [ ] Performance acceptable
- [ ] Security features working
- [ ] Database backup created
- [ ] Documentation updated

**Deployment Date**: _______________  
**Deployed By**: _______________  
**Sign-Off**: _______________  

---

## Deployment Summary

| Component | Status |
|-----------|--------|
| Build | ✅ Complete |
| Tests | ✅ 38/38 Passing |
| Coverage | ✅ 80%+ |
| WAR Package | ✅ Created |
| Tomcat Deployment | ⬜ Pending |
| User Testing | ⬜ Pending |
| Security Verification | ⬜ Pending |
| Performance Testing | ⬜ Pending |
| Go Live | ⬜ Pending |

---

**Created**: May 24, 2026  
**Application**: IT Services Portal v1.0.0  
**Environment**: Production Ready
