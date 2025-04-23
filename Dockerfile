FROM ubuntu:latest
LABEL authors="vverd"

ENTRYPOINT ["top", "-b"]