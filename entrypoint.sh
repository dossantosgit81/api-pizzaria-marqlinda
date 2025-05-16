#!/bin/sh
chown -R default:dev /var/lib/app/uploads
exec "$@"