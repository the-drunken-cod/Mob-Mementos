---
applyTo: "**"
---

# Base Instructions

Language: Java 21  
Application Type: Minecraft mod named "Mob Talismans" that adds QoL accessories (curios) providing utilities against specific mobs.
Minecraft Version: 1.21.1  
Modding Framework: NeoForge v21.1

# Conventions

- Don't give up on a problem and suggest adding a `// TODO: fix` comment. Realize dead ends and think about solutions or alternatives. Interject with questions if needed and speak up when there's an objectively better path.
- Don't add comments for the sake of comments. Code should be self-explanatory and comments reserved for explanations or important notes.
- Try to use datapack JSONs before writing any Java code.
- Use 4 spaces for indentation.
- Add `//#region` indicators for logical code sections (without `#endregion`).
- Respect the existing code style and use javax annotations like `@Override`, `@Nullable` and `@NotNull` where needed.
- In the output, instead of including unmodified members, only show the new or modified code and make use of comments like `/* existing code */`.

### Common Mistakes

- ResourceLocation: `new ResourceLocation()` is not a constructor anymore. Instead, there's now:
  - Mod location: `ResourceLocation.fromNamespaceAndPath(MobTalismans.MOD_ID, REGISTRY_NAME)`
  - Vanilla location: `ResourceLocation.withDefaultNamespace(REGISTRY_NAME)`
- Registries: Instead of `ForgeRegistries` use `BuiltInRegistries`, and also use `DeferredRegister` and `DeferredHolder` for registry management.
- Networking:
  - Instead of `PlayPayloadContext` use `IPayloadContext`
  - Instead of `context.workHandler().execute(() -> {})` use `context.enqueueWork(() -> {})`
  - Instead of `play()` use either of `PacketDistributor.sendToServer()`, `PacketDistributor.sendToPlayer()`, `PacketDistributor.sendToPlayersTrackingChunk()` or `PacketDistributor.sendToAllPlayers()`.
- NeoForge had a major refactor in 2023, so information older than that may not be applicable.
