#!/usr/bin/python

import os
import time
import smtplib
from subprocess import check_output

to = ['contact@bananaa.in', 'akashnigam010@gmail.com']
gmail_user = 'akashnigam020@gmail.com'
gmail_pwd = 'Akash123!'
subject = 'Alerts from Server - dev.bananaa.in'

def get_pid(name):
    try:
        pidstr = check_output(["pidof","-s",name])
        return int(pidstr)
    except:
        return -999;


def check_pid(pid):
    try:
        os.kill(pid, 0)
    except OSError:
        return False
    else:
        return True

while True:
    if check_pid(get_pid('java')) == False:
        print('wildfly stopped, sending alert notification!')
        try:
                smtpserver = smtplib.SMTP_SSL('smtp.gmail.com', 465)
                smtpserver.ehlo()
                smtpserver.login(gmail_user, gmail_pwd)

                header = 'To:' + ", ".join(to) + '\n' + 'From:' + gmail_user + '\n' + 'Subject:' + subject + '\n'
                msg = header + '\n Wildfly is stopped or killed. \n\n Please attend to it soon.  \n\n'

                smtpserver.sendmail(gmail_user, to, msg)
                smtpserver.close()

                print('Alert notification sent')
        except:
                print('Error occurred while sending alert notification')
    time.sleep(60)