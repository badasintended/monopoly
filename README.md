# monopoly [![1][1]][8] [![2][2]][4]  [![3][3]][5] [![6][6]][7] <img src="src/main/resources/icon.png" align="right"/>

monopoly is a Minecraft mod that'll try to unify dropped items, keeping only one type of item obtainable.    
By default, it will try to monopolize ores, ingots, plates, etc. to whatever the first item it can get.    
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
    "nbt"    : true,                   // [optional] convert items with nbt. nbt will copied.
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

### Editing config using the dumb script
You see, i also made a little script to generate config, [config_generator.py](config_generator.py)    
It'll ask you some question, but don't worry, it'll keep it a secret between you two.    
To use the script, simply download the file and run it or, if you fancy enough and use linux:
```bash
wget https://raw.githubusercontent.com/badasintended/monopoly/1.16.2/config_generator.py
python3 config_generator.py
```
It's pretty simple, so I don't think I need to explain how to use it.    
To exit and save the config simply input nothing (just press enter) on the next `Tag:` question.

[1]: https://img.shields.io/badge/minecraft-1.16+-brightgreen
[2]: https://img.shields.io/badge/loader-Fabric-blue
[3]: https://img.shields.io/badge/code_quality-F-red
[4]: https://fabricmc.net
[5]: https://git.io/code-quality
[6]: https://img.shields.io/badge/dynamic/json?color=orange&label=downloads&query=downloadCount&url=https%3A%2F%2Faddons-ecs.forgesvc.net%2Fapi%2Fv2%2Faddon%2F404223&logo=data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAACsAAAAXCAYAAACS5bYWAAAABmJLR0QA/wD/AP+gvaeTAAAB80lEQVRIib2WsUuVURjGn2NhUjSZQ7Rli6mL1SBtQYNgpi2FIOjmVEpb0H8Q1GRIpIMkQkG4VIjicKElaZEGxTGC0qFAQQX7NXynOH18373nHM+9D9zhnu993ufH+d57zzEKEPBc0niIJ6WaAusrdaHwVCjsx8icZUlvJe1H+iVJBtiS1H6cJjU0IemHpPlj9NiUVBEwTv20DRjgRYT3AJgBLv9Dts0WEgK62gVOAfcDfStA8dsGTgJPgd8JQSvABjBkgdc9fU+AEzUHA7gCLCWCncv17vXwTAVPM9AOPAQ+AD8jYXeBbqfnM+CoSv060FyNy3iAN0m6KOmS/VyQ1CqpTdJggWVG0oCkc5LWjDHXbJ9H1j9WEnXDGLNaiydawOfc7izb9e/2+1qufhb4VLCr9T9sgAe50Md2/RbZCPU5tR12DEaAvZzvXiNg24BDJ3S6Sm0n8B44C6w6nj2gpe6wFmLRCf5F2f/j/54vjuddIzj/Bt/JvdKvQD9Q+OMF7ubqJxsJ2wxMAZsF0AtAF9kpeRN47YzNPjANdDQM1oEepVjfgFcF629CM0KviGWgpyVdLXl8XtJwwfoZoCdFfpDIzv8YvQzJSbKzkm5H+vrxubSkEtmNbSdyZwGu+2al2NleZXeFWA34FqaA9Q4rkfcI/QF8wa/Bkt5sxwAAAABJRU5ErkJggg==
[7]: https://www.curseforge.com/minecraft/mc-mods/monopoly
[8]: https://minecraft.net
