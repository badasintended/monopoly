#!/usr/bin/env python3

import json
import os


def clear(): os.system('cls' if os.name == 'nt' else 'clear')


config_map = {}

if os.path.exists("monopoly.json"):
    config_map = json.load(open("monopoly.json"))

clear()
while True:
    print("Help: comma (,) to delete existing value")
    print("----------------------------------------")
    tag = input("Tag: ").lower().strip()
    if not tag:
        break
    if tag in config_map:
        prev = config_map[tag] if type(config_map[tag]) == str else config_map[tag]["target"]
        target = input(f"Target [,] ({prev}): ").lower().strip()
        if target == ",":
            config_map.pop(tag)
            clear()
            continue
        if not target:
            target = prev
    else:
        target = input("Target: ").lower().strip()
    if not target:
        break

    prev_nbt = False
    prev_exclude = []
    if tag in config_map:
        prev = config_map[tag]
        if type(prev) == dict:
            if "nbt" in prev:
                prev_nbt = prev["nbt"]
            if "exclude" in prev:
                if type(prev["exclude"]) == str:
                    prev_exclude.append(prev["exclude"])
                else:
                    prev_exclude.extend(prev["exclude"])

    nbt_input = input(f"Allow nbt ? [y/n] ({prev_nbt}): ").lower().strip()
    nbt = nbt_input == "y" if nbt_input else prev_nbt
    exclude = []
    j = 0
    while True:
        default = "" if j >= len(prev_exclude) else " [,] (" + prev_exclude[j] + ")"
        ex = input(f"Exclude {j}{default}: ").lower().strip()
        if ex == "," and default:
            j = j + 1
            continue
        if not ex:
            if not default:
                break
            ex = prev_exclude[j]
        if ex not in exclude:
            exclude.append(ex)
        j = j + 1
    if not nbt and not exclude:
        config_map[tag] = target
    else:
        config = {"target": target}
        if nbt:
            config["nbt"] = True
        if exclude:
            if len(exclude) <= 1:
                config["exclude"] = exclude[0]
            else:
                config["exclude"] = exclude
        config_map[tag] = config
    clear()

jsonConfig = json.dumps(config_map, indent=2)

clear()

file = open("monopoly.json", "w")
file.write(jsonConfig)
file.close()

print("saved")
