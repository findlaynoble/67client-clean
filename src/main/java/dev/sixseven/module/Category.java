/*
 * Decompiled with CFR 0.152.
 */
package dev.sixseven.module;

public enum Category {
    COMBAT("Combat", "combat"),
    MISC("Misc", "misc"),
    RENDER("Render", "render"),
    VISUALS("Visuals", "visuals"),
    CLIENT("Client", "client");

    private final String displayName;
    private final String iconId;

    private Category(String displayName, String iconId) {
        this.displayName = displayName;
        this.iconId = iconId;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public String getIconId() {
        return this.iconId;
    }
}

