#! /bin/bash
osascript -e 'tell app "Terminal"
    do script "cd Documents/SeleniumGrid/ &&
    java -jar selenium-server-standalone-3.5.3.jar -role hub -hubConfig hub.json"
end tell'
osascript -e 'tell app "Terminal"
    do script "cd Documents/SeleniumGrid/ &&
    java -jar -Dwebdriver.gecko.driver=/usr/local/bin/geckodriver -Dwebdriver.chrome.driver=/usr/local/bin/chromedriver selenium-server-standalone-3.5.3.jar -role node -nodeConfig node.json"
end tell'
docker run --net=host --shm-size 2g --entrypoint "-SE_NODE_MAX_SESSIONS=5" selenium/standalone-firefox:114.0.1-geckodriver-0.33.0-grid-4.10.0-20230614
