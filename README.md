# Fast or Slow Happy Ghast

Fast or Slow Happy Ghast is a server-side Fabric mod for Minecraft 26.3.

Requires Java 25 or newer and Fabric Loader 0.19.5 or newer. Fabric API is not required.

It changes the speed of harnessed Happy Ghasts only.
Wild or unharnessed ones stay at the vanilla speed. Harnessed ones can have one speed while idle and another while being ridden.

## Build

Use JDK 25 or newer.

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

Speeds must be finite and non-negative. Invalid config values fall back to the defaults.

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
