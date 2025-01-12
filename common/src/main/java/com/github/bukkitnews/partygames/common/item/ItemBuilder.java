package com.github.bukkitnews.partygames.common.item;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class ItemBuilder {

    private final ItemStack itemStack;
    private final ItemMeta itemMeta;

    /**
     * Constructs an ItemBuilder with the specified material.
     *
     * @param material The material of the item to be created.
     */
    public ItemBuilder(Material material) {
        this.itemStack = new ItemStack(material);
        this.itemMeta = this.itemStack.getItemMeta();
    }

    /**
     * Sets the display name of the item.
     *
     * @param name The display name to set.
     * @return The current ItemBuilder instance for chaining.
     */
    public ItemBuilder setDisplayname(final String name) {
        this.itemMeta.setDisplayName(name);
        return this;
    }

    /**
     * Adds an enchantment to the item.
     *
     * @param enchantment The enchantment to add.
     * @param level       The level of the enchantment.
     * @return The current ItemBuilder instance for chaining.
     */
    public ItemBuilder addEnchantment(Enchantment enchantment, int level) {
        this.itemMeta.addEnchant(enchantment, level, true);
        this.itemStack.addUnsafeEnchantment(enchantment, level);
        return this;
    }

    /**
     * Sets the amount of the item.
     *
     * @param amount The amount to set.
     * @return The current ItemBuilder instance for chaining.
     */
    public ItemBuilder setAmount(final int amount) {
        this.itemStack.setAmount(amount);
        return this;
    }

    /**
     * Sets the lore (description) of the item.
     *
     * @param lore The lore to set.
     * @return The current ItemBuilder instance for chaining.
     */
    public ItemBuilder setLore(final String... lore) {
        this.itemMeta.setLore(Arrays.asList(lore));
        return this;
    }

    /**
     * Makes the item unbreakable.
     */
    public ItemBuilder setUnbreakable() {
        this.itemMeta.setUnbreakable(true);
        return this;
    }

    /**
     * Makes the item glow by adding a hidden enchantment.
     */
    public ItemBuilder setGlowing() {
        this.itemMeta.addEnchant(Enchantment.UNBREAKING, 1, true);
        this.itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        return this;
    }

    /**
     * Sets the custom model data for the item. This is used for custom textures in resource packs.
     *
     * @param data The custom model data to set.
     * @return The current ItemBuilder instance for method chaining.
     */
    public ItemBuilder setCustomModelData(int data) {
        this.itemMeta.setCustomModelData(data);
        return this;
    }

    /**
     * Sets the durability of the item (how much damage it can take before breaking).
     *
     * @param durability The durability value to set.
     * @return The current ItemBuilder instance for method chaining.
     */
    public ItemBuilder setDurability(int durability) {
        if (this.itemMeta instanceof Damageable) {
            ((Damageable) this.itemMeta).setDamage(durability);
        }
        return this;
    }

    /**
     * Builds the final ItemStack with the applied settings.
     *
     * @return The resulting ItemStack.
     */
    public ItemStack build() {
        this.itemStack.setItemMeta(this.itemMeta);
        return this.itemStack;
    }
}

