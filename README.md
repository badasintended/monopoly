# monopoly [![2][2]][4]  [![3][3]][5] <img src="src/main/resources/icon.png" align="right"/>

monopoly is a Minecraft mod that tries to unifyConfig dropped items, keeping only one type of item optainable.    
**Configuration is required to monopoly.**

### Config Options
Config is saved on `.minecraft/config/monopoly.json`.   
To reload the config, simply run `/reload` command    
For common tags used by various mods, see [tutorial:tags](https://fabricmc.net/wiki/tutorial:tags).
```json5
// this is not valid config since JSON doesn't support comments!
{
  // the key is item tags.
  // by default, monopoly won't change any items that has nbt data
  // or items that thrown by player.
  "c:copper_ingots": "techreborn:copper_ingot",
  
  // you need to set it too!
  "c:steel_ingots": {
    "target": "techreborn:steel_ingot",
    "nbt"   : true, // [optional] allow converting items with nbt. nbt will be copied.
    "thrown": true  // [optional] convert items thrown by player.
  }
}
```

[2]: https://img.shields.io/badge/loader-Fabric-blue
[3]: https://img.shields.io/badge/code_quality-F-red
[4]: https://fabricmc.net
[5]: https://git.io/code-quality
