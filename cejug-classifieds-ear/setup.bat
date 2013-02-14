@echo off
ver

rem Loading the .passwords file
set PASS_FILE="%1"
if "%1" == "/clean" set PASS_FILE="%USERPROFILE%\.passwords"
if "%1" == "" set PASS_FILE="%USERPROFILE%\.passwords"

for /f %%P in ('type %PASS_FILE%') do SET %%P

CALL mysql -v -u%MYSQL_USER% -hlocalhost -P3306 -p%MYSQL_PASSWORD% -e "create database IF NOT EXISTS  classifieds;"

ECHO _________
ECHO ######### JDBC Connection Pool jdbc/classifiedsPool
CALL asadmin --user=%ASADMIN_USER% delete-jdbc-connection-pool --cascade=true jdbc/classifiedsPool 
IF NOT "%1"=="/clean" (
	CALL asadmin create-jdbc-connection-pool --datasourceclassname com.mysql.jdbc.jdbc2.optional.MysqlConnectionPoolDataSource --restype javax.sql.ConnectionPoolDataSource --property "User=%MYSQL_USER%:Password=%MYSQL_PASSWORD%:URL=%DB_URL%" jdbc/classifiedsPool
)

ECHO _________
ECHO ######### JDBC Data Source jdbc/classifieds
CALL asadmin delete-jdbc-resource jdbc/classifieds
IF NOT "%1"=="/clean" (
	CALL asadmin --user=%ASADMIN_USER% create-jdbc-resource --connectionpoolid jdbc/classifiedsPool jdbc/classifieds	
)

ECHO _________
ECHO ######### JMS Connection Factory RegistrationQueueConnectionFactory
CALL asadmin --passwordfile=%PASS_FILE% delete-jms-resource RegistrationQueueConnectionFactory
IF NOT "%1"=="/clean" (
	CALL asadmin create-jms-resource --restype=javax.jms.QueueConnectionFactory --description="Cejug-Classifieds Registration Connection Factory." RegistrationQueueConnectionFactory
)

ECHO _________
ECHO ######### JMS Queue RegistrationQueue
CALL asadmin --passwordfile=%PASS_FILE% delete-jms-resource RegistrationQueue
IF NOT "%1"=="/clean" (
	CALL asadmin create-jms-resource --restype=javax.jms.Queue --description="Queue used to register Cejug-Classifieds users." RegistrationQueue
)

ECHO _________
ECHO ######### JMS Connection Factory NotificationQueueConnectionFactory
CALL asadmin --passwordfile=%PASS_FILE% delete-jms-resource NotificationQueueConnectionFactory
IF NOT "%1"=="/clean" (
	CALL asadmin create-jms-resource --restype=javax.jms.QueueConnectionFactory --description="Cejug-Classifieds Notification Connection Factory." NotificationQueueConnectionFactory
)

ECHO _________
ECHO ######### JMS Queue NotificationQueue
CALL   asadmin --passwordfile=%PASS_FILE% delete-jms-resource NotificationQueue
IF NOT "%1"=="/clean" (
	CALL asadmin create-jms-resource --restype=javax.jms.Queue --description="Queue used to notify Cejug-Classifieds users about their registration." NotificationQueue
)

ECHO _________
ECHO ######### JavaMail mail/classifieds
CALL   asadmin --passwordfile=%PASS_FILE% delete-javamail-resource mail/classifieds
IF NOT "%1"=="/clean" (
	CALL asadmin --interactive=false create-javamail-resource --mailhost=%MAIL_HOST% --mailuser=%MAIL_USER% --fromaddress=%MAIL_FROM% --enabled=true --description="e-Mail account used to confirm the registration of the Arena PUJ users" --storeprotocol=imap --storeprotocolclass=com.sun.mail.imap.IMAPStore --transprotocol smtp --transprotocolclass com.sun.mail.smtp.SMTPSSLTransport --property mail-smtp.user=%MAIL_SMTP_USER%:mail-smtp.port=465:mail-smtp.password=%MAIL_SMTP_PASSWORD%:mail-smtp.auth=true:mail-smtp.socketFactory.fallback=false:mail-smtp.socketFactory.class=javax.net.ssl.SSLSocketFactory:mail-smtp.socketFactory.port=%MAIL_SMTP_PORT%:mail-smtp.starttls.enable=true mail/classifieds
)

ECHO _________
ECHO ######### Deploying to Glassfish v3.x"
CALL asadmin --user %ASADMIN_USER% deploy --force=true target/cejug-classifieds-ear-*.ear