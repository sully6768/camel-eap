# Camel on EAP Clustered Quartz Example

Installing Camel Quartz in EAP is rather straightforward.  The challenge comes when one needs to have the components perform as a singleton in a cluster of EAP servers.

## Prerequisites
To work with this example you will need to install and configure a datasource to manage cluster.  For this example I chose to use MySQL but any Quartz supported JDBC datastore will do.

* MySQL (I installed the latest 5.7.13 but any should do) [Download](http://dev.mysql.com/downloads/mysql/ "MySQL Download")  
* MySQL Driver [Download](https://dev.mysql.com/downloads/connector/j/ "MySQL Driver Download") 
* Quartz 2.2.x [Download](http://d2zwv9pap9ylyd.cloudfront.net/quartz-2.2.3-distribution.tar.gz "Download")  

