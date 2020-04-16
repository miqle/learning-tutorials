from aws_cdk import core
from aws_cdk import aws_ec2 as ec2

class VpcStack(core.Stack):

    def __init__(self, scope: core.Construct, id: str, **kwargs) -> None:
        super().__init__(scope, id, **kwargs)
        vpc = ec2.Vpc(self, "VPC", cidr="10.1.0.0/16")
        
