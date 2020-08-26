# monopoly [![2][2]][4]  [![3][3]][5] <img src="src/main/resources/icon.png" align="right"/>

monopoly is a Minecraft mod that'll try to unify dropped items, keeping only one type of item obtainable. By default, it will try to monopolize ores, ingots, plates, etc. to whatever the first item it can get.    
**Manual configuration is recommended to monopoly the correct market.**

### Config Options
Config is saved on `.minecraft/config/monopoly.json`.   
To reload the config, simply run `/reload` command    
For common tags used by various mods, see [tutorial:tags](https://fabricmc.net/wiki/tutorial:tags).
```json5
// this is not valid config since JSON doesn't support comments!
{
  // the key is item tags.
  // this is a shorthand of nbt=false
  "c:copper_ingots": "techreborn:copper_ingot",
  
  "c:steel_ingots": {
    "target" : "techreborn:steel_ingot",
    "nbt"    : true,                   // [optional] allow converting items with nbt. nbt will be copied.
    "exclude": "astromine:steel_ingot" // [optional] this wont be converted.
  },

  "c:iron_dusts": {
    "target" : "indrev:iron_dust",
    "exclude": [ // accept array too
      "appliedenergistics2:iron_dust",
      "astromine:iron_dust"
    ]
  }
}
```

[2]: https://img.shields.io/badge/loader-Fabric-blue
[3]: https://img.shields.io/badge/code_quality-F-red
[4]: https://fabricmc.net
[5]: https://git.io/code-quality
