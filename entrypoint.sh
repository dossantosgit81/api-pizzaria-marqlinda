#!/bin/sh
set -e

chown -R appuser:appgroup /var/lib/app/uploads

exec su-exec appuser "$@"