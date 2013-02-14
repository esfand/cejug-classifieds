#!/bin/bash

if [ -n "$1" -a -f $1 ]; then
    . $1;export PASS_FILE="$1";
    if [ -n "$2" -a "$2" = "/clean" ]; then
        export CLEAN="/clean"
    fi
else
    . $HOME/.passwords;export PASS_FILE="$HOME/.classifieds";
fi

GF_VERSION=$(asadmin version -t)

echo "$GF_VERSION" | grep -q 'v2.'
if [ $? -eq 0 ] ; then
    echo "Sorry, you need to install and configure the Glassfish V3 before to run this project.";
    echo "You can get the newest Glassfish version here: http://download.java.net/glassfish/v3/promoted/";
    exit 1;
else
    mysql -v -u$MYSQL_USER -hlocalhost -P3306 -p$MYSQL_PASSWORD -e "create database IF NOT EXISTS  classifieds;"
    
    #echo "Shell Script for Glassfish v3.x"
    echo
    echo "-------- JDBC Connection Pool jdbc/classifiedsPool"
    asadmin --user=$ASADMIN_USER delete-jdbc-connection-pool --cascade=true jdbc/classifiedsPool   
    if [ -z "$CLEAN" ]; then jdbc/arenaPool
        asadmin create-jdbc-connection-pool --datasourceclassname com.mysql.jdbc.jdbc2.optional.MysqlConnectionPoolDataSource --restype javax.sql.ConnectionPoolDataSource --property "User=$MYSQL_USER:Password=$MYSQL_PASSWORD:URL=$DB_URL" jdbc/classifiedsPool
    fi
    echo
    echo "-------- JDBC Data Source jdbc/classifieds"
    asadmin --user=$ASADMIN_USER delete-jdbc-resource jdbc/classifieds
    if [ -z "$CLEAN" ]; then
        asadmin --user=$ASADMIN_USER create-jdbc-resource --connectionpoolid jdbc/classifiedsPool jdbc/classifieds
    fi
    
    #echo "Shell Script for Glassfish v3.x"
    echo
    echo "-------- JDBC Connection Pool jdbc/classifiedsPool"
    asadmin --user=$ASADMIN_USER delete-jdbc-connection-pool --cascade=true jdbc/classifiedsPool   
    if [ -z "$CLEAN" ]; then jdbc/arenaPool
        asadmin create-jdbc-connection-pool --datasourceclassname com.mysql.jdbc.jdbc2.optional.MysqlConnectionPoolDataSource --restype javax.sql.ConnectionPoolDataSource --property "User=$MYSQL_USER:Password=$MYSQL_PASSWORD:URL=$DB_URL" jdbc/classifiedsPool
    fi
    echo
    echo "-------- JDBC Data Source jdbc/classifieds"
    asadmin --user=$ASADMIN_USER delete-jdbc-resource jdbc/classifieds
    if [ -z "$CLEAN" ]; then
        asadmin --user=$ASADMIN_USER create-jdbc-resource --connectionpoolid jdbc/classifiedsPool jdbc/classifieds
    fi

    echo
    echo "-------- JMS Connection Factory RegistrationQueueConnectionFactory"
    asadmin --passwordfile=$PASS_FILE delete-jms-resource RegistrationQueueConnectionFactory
    if [ -z "$CLEAN" ]; then
        asadmin create-jms-resource --restype=javax.jms.QueueConnectionFactory --description="Cejug-Classifieds Registration Connection Factory." RegistrationQueueConnectionFactory
    fi
    echo
    echo "-------- JMS Queue RegistrationQueue"
    asadmin --passwordfile=$PASS_FILE delete-jms-resource RegistrationQueue
    if [ -z "$CLEAN" ]; then
        asadmin create-jms-resource --restype=javax.jms.Queue --description="Queue used to register Cejug-Classifieds users." RegistrationQueue
    fi

    echo
    echo "-------- JMS Connection Factory NotificationQueueConnectionFactory"
    asadmin --passwordfile=$PASS_FILE delete-jms-resource NotificationQueueConnectionFactory
    if [ -z "$CLEAN" ]; then
        asadmin create-jms-resource --restype=javax.jms.QueueConnectionFactory --description="Cejug-Classifieds Notification Connection Factory." NotificationQueueConnectionFactory
    fi
    echo
    echo "-------- JMS Queue NotificationQueue"
    asadmin --passwordfile=$PASS_FILE delete-jms-resource NotificationQueue
    if [ -z "$CLEAN" ]; then
        asadmin create-jms-resource --restype=javax.jms.Queue --description="Queue used to notify Cejug-Classifieds users about their registration." NotificationQueue
    fi
    echo
    echo "-------- JavaMail mail/classifieds"
    asadmin --passwordfile=$PASS_FILE delete-javamail-resource mail/classifieds
    if [ -z "$CLEAN" ]; then
        asadmin --interactive=false create-javamail-resource --mailhost=$MAIL_HOST --mailuser=$MAIL_USER --fromaddress=$MAIL_FROM --enabled=true --description="e-Mail account used to confirm the registration of the Arena PUJ users" --storeprotocol=imap --storeprotocolclass=com.sun.mail.imap.IMAPStore --transprotocol smtp --transprotocolclass com.sun.mail.smtp.SMTPSSLTransport --property mail-smtp.user=$MAIL_SMTP_USER:mail-smtp.port=465:mail-smtp.password=$MAIL_SMTP_PASSWORD:mail-smtp.auth=true:mail-smtp.socketFactory.fallback=false:mail-smtp.socketFactory.class=javax.net.ssl.SSLSocketFactory:mail-smtp.socketFactory.port=$MAIL_SMTP_PORT:mail-smtp.starttls.enable=true mail/classifieds
    fi

    echo
    echo "-------- Deploying to Server $GF_VERSION"
    asadmin --user $ASADMIN_USER deploy --force=true target/cejug-classifieds-ear-*.ear
fi
