This Exercise is here to make it easy to deploy all Exercises of this Training in one single
step deploying it as a feature.

Deployment:

In order to deploy it please go to the parent directory 'camel-exercise' and build ALL exercises using:

    mvn clean install

The start up JBoss Fuse and loads the features file into the Karaf engine:

    features:addurl mvn:com.redhat.gpe.training/camel-exercise-features/1.0/xml/features

verify this by checking the features list:

    features:list | grep camel-exercise

Note

    The grep command will remove all entries that does not contain the given text.

You will see a list like this:

---------------------------------------------------------------------------------
karaf@root> features:list | grep camel-exercise
[uninstalled] [0.0.0               ] camel-exercise-cbr                   repo-0
[uninstalled] [0.0.0               ] camel-exercise-splitter              repo-0
[uninstalled] [0.0.0               ] camel-exercise-aggregator            repo-0
[uninstalled] [0.0.0               ] camel-exercise-jms-transaction       repo-0
[uninstalled] [0.0.0               ] camel-exercise-custom-file-component repo-0
---------------------------------------------------------------------------------

Now in order to deploy / install any of these components you simple do (here the CBR exercise is used to demo
it):

    features:install camel-exercise-cbr

Check your list again and you see:

[installed  ] [0.0.0               ] camel-exercise-cbr                   repo-0
