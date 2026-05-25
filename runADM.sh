#!/bin/bash

kotlin -cp ".:$(printf %s: lib/*.jar)" AdministradorDeMusicaKt $*