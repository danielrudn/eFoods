#!/usr/bin/perl -w
use CGI qw(:all);
use CGI::Carp qw(fatalsToBrowser);
use strict;
##################################
my $request = CGI->new;
my $back = $request->param('back');
my $nonce = $request->param('secret');
my $user = $request->remote_user();
my $fullName = `finger $user | grep Name | awk -F "Name: " '{printf \$2}'`;
my $hash = `echo -n "$user"magic"$nonce" | sha1sum | awk '{ printf \$1 }'`;
print "Location: $back?user=$user&fullName=$fullName&hash=$hash\n\n";

