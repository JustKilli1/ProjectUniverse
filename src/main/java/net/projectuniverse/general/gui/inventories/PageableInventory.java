package net.projectuniverse.general.gui.inventories;

import net.kyori.adventure.text.Component;
import net.minestom.server.inventory.InventoryType;
import net.minestom.server.item.ItemStack;
import net.minestom.server.item.Material;
import net.projectuniverse.general.gui.items.ClickableItem;

import java.util.List;

/**
 * This class represents a pageable inventory that extends the SingletonInventory class.
 * It allows for creating inventories with multiple pages, where each page displays a subset of elements from a given list.
 *
 * @param <T> the type of elements in the inventory
 */

public abstract class PageableInventory<T> extends SingletonInventory {

    private final List<T> elements;
    private final int[] possibleSlots;
    private int currentPage = 1;

    private static final ItemStack NEXT_PAGE_ITEM = ItemStack.of(Material.ARROW).withDisplayName(Component.text("§8» §bNächste §7Seite"));
    private static final ItemStack BE_PAGE_ITEM = ItemStack.of(Material.ARROW).withDisplayName(Component.text("§8» §bVorherige §7Seite"));
    private final ClickableItem localNextPageItem = new ClickableItem(NEXT_PAGE_ITEM, player -> buildPage(++currentPage));
    private final ClickableItem localBehaviorPageItem = new ClickableItem(BE_PAGE_ITEM, player -> buildPage(--currentPage));

    public PageableInventory(InventoryType inventoryType, String title, boolean clickable, int[] possibleSlots, List<T> values) {
        super(inventoryType, clickable, title);

        this.elements = values;
        this.possibleSlots = possibleSlots;

        this.fill();
        buildPage(1);
    }

    public void fill() {}

    /**
     * Calculates the next available slot in the inventory.
     *
     * @return The index of the next available slot in the inventory, starting from 0.
     */
    public int calculateNextPageSlot() {
        return this.getInventory().getSize() - 1;
    }

    /**
     * Calculates the slot index for the behavior page in the inventory.
     *
     * @return The index of the behavior page slot in the inventory, starting from 0.
     */
    public int calculateBehaviorPageSlot() {
        return this.getInventory().getSize() - 9;
    }

    /**
     * Calculates the slot index for the page count slot in the inventory.
     *
     * @return The index of the page count slot in the inventory, starting from 0.
     */
    public int calculatePageCountSlot() {
        return this.getInventory().getSize() - 5;
    }

    /**
     * Called when the page of the inventory is changed.
     *
     * @param inventory The PageableInventory for which the page is being changed.
     */
    public void onPageChange(PageableInventory<T> inventory) {}

    /**
     * Builds the page based on the provided ID.
     *
     * @param id The ID of the page to be built.
     */
    public void buildPage(int id) {

        this.currentPage = id;
        this.clear();

        if (currentPage > 1) {
            setItem(calculateBehaviorPageSlot(), localBehaviorPageItem);
        } else {
            removeItem(calculateBehaviorPageSlot());
        }

        if (elements.size() == possibleSlots.length) {
            removeItem(calculateNextPageSlot());
        } else if (currentPage < getMaximalPage()) {
            setItem(calculateNextPageSlot(), localNextPageItem);
        } else {
            removeItem(calculateNextPageSlot());
        }

        ItemStack pageCountItem = ItemStack.of(Material.PAPER).withDisplayName(Component.text(currentPage + " / " + getMaximalPage()));
        setItem(calculatePageCountSlot(), pageCountItem, null);

        int stepID = 0;
        for (T element : elements.subList(possibleSlots.length * (currentPage - 1), Math.min(elements.size(), possibleSlots.length * (currentPage - 1) + possibleSlots.length))) {
            setItem(possibleSlots[stepID], constructItem(element));
            stepID++;
        }
        onPageChange(this);
    }

    /**
     * Clears the items in the inventory slots.
     */
    public void clear() {
        for (int slot : possibleSlots) {
            getInventory().setItemStack(slot, ItemStack.AIR);
        }
    }

    public int getMaximalPage() {
        return (int) Math.ceil((double) elements.size() / (double) possibleSlots.length);
    }

    /**
     * Constructs a clickable item from the given value.
     *
     * @param value The value used to construct the clickable item.
     * @return The constructed clickable item.
     */
    public abstract ClickableItem constructItem(T value);

    public int getCurrentPage() {
        return currentPage;
    }
}
