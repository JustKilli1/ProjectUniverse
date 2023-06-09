package net.projectuniverse.general.gui.inventories;

import net.kyori.adventure.text.Component;
import net.minestom.server.inventory.InventoryType;
import net.minestom.server.item.ItemStack;
import net.minestom.server.item.Material;
import net.projectuniverse.general.gui.items.ClickableItem;

import java.util.List;

public abstract class PageableInventory<T> extends SingletonInventory {

    private int currentPage = 1;
    private static final ItemStack NEXT_PAGE_ITEM = ItemStack.of(Material.ARROW).withDisplayName(Component.text(">> Nächste Seite"));
    private static final ItemStack PREVIOUS_PAGE_ITEM = ItemStack.of(Material.ARROW).withDisplayName(Component.text(">> Vorherige Seite"));
    private final ClickableItem localNextPageItem = new ClickableItem(NEXT_PAGE_ITEM, player -> buildPage(++currentPage));
    private final ClickableItem localPreviousPageItem = new ClickableItem(NEXT_PAGE_ITEM, player -> buildPage(--currentPage));
    private final List<T> elements;
    private int[] possibleSlots;


    public PageableInventory(InventoryType type, String title, boolean clickable, int[] possibleSlots, List<T> elements) {
        super(type, title, clickable);

        this.elements = elements;
        this.possibleSlots = possibleSlots;
        buildPage(currentPage);
    }

    public int calculateNextPageSlot() { return getInventory().getSize() - 1; }
    public int calculatePreviousPageSlot() { return getInventory().getSize() - 9; }

    public void buildPage(int page) {
        this.currentPage = page;
        this.clear();

        if(currentPage > 1) setItem(calculatePreviousPageSlot(), localPreviousPageItem);
        else removeItem(calculatePreviousPageSlot());

        if(elements.size() == possibleSlots.length) removeItem(calculateNextPageSlot());
        else if(currentPage < getMaxPage()) setItem(calculateNextPageSlot(), localNextPageItem);
        else removeItem(calculateNextPageSlot());
        
        int stepId = 0;

        System.out.println(stepId);
        for(T element : elements.subList(possibleSlots.length * (currentPage - 1), Math.min(elements.size(), possibleSlots.length + (currentPage - 1) + possibleSlots.length))) {
            System.out.println(stepId);
            setItem(stepId, constructItem(element));
            stepId++;
        }
        
    }

    private int getMaxPage() {
        return elements.size() / possibleSlots.length;
    }

    public abstract ClickableItem constructItem(T value);

    private void clear() {
        for(int possibleSlot : possibleSlots) {
            getInventory().setItemStack(possibleSlot, ItemStack.AIR);
        }
    }
}
