#!/usr/bin/env python3

from optparse import OptionParser
import sys
import boto3


#
# Will Create AWS Resources.  This script will prompt user for input during the creation
# 
def main():

    parser = OptionParser(usage="usage: %prog [options] queueName", version="%prog 1.0")
    # parser.add_option("-x", "--xhtml",
    # action="store_true",
    # dest="xhtml_flag",
    # default=False,
    # help="create a XHML template instead of HTML")

    # parser.add_option("-n", "--name",
    #                   type="choice",
    #                   action="store",  # option because action default to store
    #                   dest="format",
    #                   help="enter a queuename")

    (options, args) = parser.parse_args()

    print("options entered : ", options)
    print("args entered : ", args)


    # Print out Existing SQS Queues
    index = 1
    print('### List of Existing Queue Names ###')
    sqs = boto3.resource('sqs')
    for queue in sqs.queues.all():
        url = queue.url
        filename = url.split("/")[-1]
        print("\t", index,  ") ",  filename)
        index = index + 1
    print("### ###")

    # Get new queue name
    sqsQueueName = input("Please Enter Your SQS Queue Name! ")
    print("Name Entered=", sqsQueueName)

    # Create Queue in AWS

    #file_contents = open(filename)
    #print("contents=", file_contents)

    # Lets read the contents of this file
    #file_contents.read()


# Query for the list of SQS Queue names in Reqion 
def get_existing_queue_names():
  print("get_existing_queue_names()")
  




if __name__ == "__main__":
    main()
        
