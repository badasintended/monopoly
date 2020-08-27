#!/usr/bin/env python3

import json
import os


def clear(): os.system('cls' if os.name == 'nt' else 'clear')


configMap = {}

i = 0
clear()
print("Total: 0")
while True:
    tag = input("Tag: ").lower().strip()
    if not tag:
        break
    target = input("Target: ").lower().strip()
    nbt = input("Allow nbt ? [y/n] (n):").lower().strip() == "y"
    exclude = []
    j = 0
    while True:
        j = j + 1
        ex = input(f"Exclude {j}: ").lower().strip()
        if not ex:
            break
        exclude.append(ex)
    if not nbt and not exclude:
        configMap[tag] = target
    else:
        config = {"target": target}
        if nbt:
            config["nbt"] = True
        if exclude:
            if len(exclude) <= 1:
                config["exclude"] = exclude[0]
            else:
                config["exclude"] = exclude
        configMap[tag] = config
    clear()
    i = i + 1
    print(f"Total: {i}")

jsonConfig = json.dumps(configMap, indent=2)

clear()

file = open("monopoly.json", "w")
file.write(jsonConfig)
file.close()

print("saved")
