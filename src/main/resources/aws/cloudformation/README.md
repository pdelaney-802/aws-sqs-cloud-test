

# Executes Cloudformation creation Stack via command line

Notes on creation of the AWS Resources for this project

Will need to have install python, pip, then the aws-cli command line tools

Instructions on how to perform installation
https://docs.aws.amazon.com/cli/latest/userguide/cli-chap-install.html


### Create new Stack
Execute command line aws to create this stack

```
aws cloudformation create-stack --stack-name <stack-name> --template-body file://create-aws-resources.json
```

### Delete Stack

```
aws cloudformation delete-stack --stack-name <stack-name>
```

### Show Stack Status
Shows you the status of the Stack creation when it is being created or deleted.

```
aws cloudformation describe-stack-events --stack-name <stack-name> | less
```


### Sending Messages to the SQS Queue

Messages are passed to the SQS Queue via CloudWatch Scheduler Event.  

You can also send messages to the queue via the following command line parameters.

```
aws sqs send-message .......
```
