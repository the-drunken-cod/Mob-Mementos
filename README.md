<div style="text-align: center;" align="center">

# ![Mob Talismans](./.github/assets/banner.jpg)  

NeoForge mod for Minecraft Java 1.21.1 that adds QoL accessories (curios) providing utilities against annoying mobs.  
**Requires the mod [Accessories.](https://modrinth.com/mod/accessories/versions?l=neoforge&g=1.21.1)**

---

### [Wiki](https://github.com/the-drunken-cod/MobTalismans/wiki) &bull; [Content](#content) &bull; [Intents&nbsp;&amp;&nbsp;Goals](#intents--goals) &bull; [Installation](#installation) &bull; [Modpack&nbsp;Policy](#modpack-policy) &bull; [Ideas&nbsp;&amp;&nbsp;Roadmap](#ideas--roadmap) &bull; [Attribution](#attribution) &bull; [Disclaimer](#disclaimer)

---

</div>

<br>

<br>

## Content
### Talismans
- **Conduit Necklace**: hurts aggressive aquatic mobs in the vicinity
- **Creeper Belt**: prevents nearby creepers from doing block damage
- **Enderman Anklet**: prohibits nearby endermen from picking up blocks
- **Silverfish Bangle**: converts infested blocks nearby to their non-infested variant
- **Phantom Cloak**: deters phantoms, just like cats do

<br>

## Intents & Goals
- Provide useful utilities against annoying mobs
- Enhance gameplay without conveying a feeling of becoming too overpowered or trivializing mob interactions
- Occupy the more rarely used accessory slots
- Be modpack friendly (many config options, easy gating of progression when altering recipes)

<br>

## Installation
1. Create a **Minecraft Java Edition v1.21.1 instance with [the NeoForge modloader.](https://neoforged.net/)**  
  Using a launcher like [ATLauncher](https://atlauncher.com/) or [MultiMC](https://multimc.org/) will simplify this process.
2. Download the latest Mob Talismans mod jar from the [**releases page.**](https://github.com/the-drunken-cod/MobTalismans/releases)
3. Download the latest version of the mod [**Accessories.**](https://modrinth.com/mod/accessories/versions?l=neoforge&g=1.21.1)
4. Place both .jar files into the `mods` folder of your Minecraft instance.
5. Launch the game and enjoy!  
  After launching, you can modify the config file via the in-game options menu (Mods > "Mob Talismans" > Config) or by editing the file located at `./config/mobtalismans-startup.toml` in your Minecraft instance folder.  
  A restart might be required for the more fundamental changes to take effect (i.e. item properties like durability, enabling/disabling items, etc.).

<br>

## Modpack Policy
You are free to use Mob Talismans in any modpacks; public or private :)  
Just make sure you abide by [the Minecraft EULA](https://minecraft.net/en-us/eula)<!-- (even if they themselves don't) -->, common sense, and [our license.](./LICENSE.txt)  
Please also [report any issues or suggestions](https://github.com/the-drunken-cod/MobTalismans/issues), so we can improve the mod for you and other players and have better compatibility with other mods.

<br>

## Ideas & Roadmap
- &lt;P1&gt; Accessories that are somewhat expensive to make that make mobs less annoying or offer other utilities tied to mobs
    - [x] &lt;P1&gt; Durability that is used up when the effect triggers
    - [x] &lt;P1&gt; Talismans:
        - [x] &lt;P1&gt; **Conduit Necklace**: hurts aggressive aquatic mobs in the vicinity
        - [x] &lt;P1&gt; **Creeper Belt**: prevents nearby creepers from doing block damage
        - [x] &lt;P1&gt; **Enderman Anklet**: prohibits nearby endermen from picking up blocks
        - [x] &lt;P1&gt; **Silverfish Bangle**: converts infested blocks nearby to their non-infested variant
        - [x] &lt;P1&gt; **Phantom Cloak**: deters phantoms, just like cats do
    - [ ] &lt;P2&gt; Rendering models
    - [ ] &lt;P2&gt; Recharging mechanic
    - [ ] &lt;P3&gt; More Talismans:
        - [ ] &lt;P3&gt; **Bat** (glasses): prevents bats from spawning nearby
        - [ ] &lt;P3&gt; **Bee**: prevents beehives from becoming agitated when harvesting or breaking them
        - [ ] &lt;P3&gt; **Frog**: drops froglight when eating normal slimes
        - [ ] &lt;P3&gt; **Ghast**: reflects ghast fireballs
        - [ ] &lt;P3&gt; **Glow Squid** (face/hat): underwater night vision
        - [ ] &lt;P3&gt; **Hoglin**: repels hoglins like warped fungi
        - [ ] &lt;P3&gt; **Piglin**: prevents piglins from becoming hostile when opening chests or mining gold blocks
        - [ ] &lt;P3&gt; **Shulker**: applies slow falling after levitation runs out
        - [ ] &lt;P3&gt; **Vex**: makes vex despawn faster (or smaller radius around their summoning evoker)
        - [ ] &lt;P3&gt; **Wandering Trader** (charm): prevents wandering traders from spawning in a big radius
        - [ ] &lt;P3&gt; **Warden** (shoes): shriekers have a chance to not activate, the warden can't sniff the player anymore
        - [ ] &lt;P3&gt; **Zombie** (charm): prevents zombies from spawning reinforcements and breaking down doors
- [ ] &lt;P4&gt; Placeable Charms:
    - **Evoker**: used on beds & respawn anchors to make them a single-use fallback spawnpoint
        - Any other bed can be used as a temporary spawnpoint, but if they're broken, the player will respawn at their fallback bed/anchor
        - Chat message when the charmed bed/anchor is used or broken to inform the player

<br>

## Attribution
- Banner font created with [textstudio.com](https://www.textstudio.com/logo/minecraft-logo-generator-697)

<br>

## Disclaimer
NOT AN OFFICIAL MINECRAFT PRODUCT. NOT APPROVED BY OR ASSOCIATED WITH MOJANG OR MICROSOFT.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.
