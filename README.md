# Fast or Slow Happy Ghast

Fast or Slow Happy Ghast is a server-side Fabric mod for Minecraft 26.2.

It changes the speed of harnessed Happy Ghasts only.
Wild or unharnessed ones stay at the vanilla speed. Harnessed ones can have one speed while idle and another while being ridden.

## Build

```sh
./gradlew build
```

The built mod jar is created in `build/libs/`.

## Config

On first launch the mod creates:

```text
config/fasterhappyghast.properties
```

Default values:

```properties
tamed-idle-speed=0.05
ridden-speed=0.17
```

`tamed-idle-speed` is for a harnessed Happy Ghast with no passengers.
`ridden-speed` is for one that is being ridden.

## Commands

All commands require permission level 2.

```text
/happyghast
/happyghast help
/happyghast get
/happyghast reload
/happyghast set tamed-idle-speed <speed>
/happyghast set ridden-speed <speed>
```
