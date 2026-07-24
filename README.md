# Faster Happy Ghast

Faster Happy Ghast is a server-side Fabric mod for Minecraft 26.2 that lets you configure Happy Ghast speed by state.

Untamed/unharnessed Happy Ghasts keep the vanilla flying speed. Harnessed Happy Ghasts use one configurable speed while idle and another configurable speed while ridden.

## Build

```sh
./gradlew build
```

The built mod jar is created in `build/libs/`.

## Config

On first launch, the mod creates:

```text
config/fasterhappyghast.properties
```

Default values:

```properties
tamed-idle-speed=0.05
ridden-speed=0.17
```

`tamed-idle-speed` controls harnessed Happy Ghasts with no passengers. `ridden-speed` controls harnessed Happy Ghasts with passengers.

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
