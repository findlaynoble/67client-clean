/*
 * Decompiled with CFR 0.152.
 */
package dev.sixseven.gui.panel;

import dev.sixseven.gui.ClickGuiState;
import dev.sixseven.gui.panel.ModuleEntry;
import dev.sixseven.gui.panel.Panel;
import dev.sixseven.module.Category;
import dev.sixseven.module.Module;
import dev.sixseven.module.ModuleManager;
import dev.sixseven.render.nanovg.NVGIcons;
import dev.sixseven.render.nanovg.NVGRenderer;
import dev.sixseven.rt.Deobf;
import dev.sixseven.theme.ThemeManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CategoryPanel
extends Panel {
    private static final float ENTRY_GAP = 3.0f;
    private static final float ENTRY_INSET = 6.0f;
    private final Category category;
    private final List<ModuleEntry> entries = new ArrayList<ModuleEntry>();
    private String filter = Deobf.decrypt("");

    public CategoryPanel(Category category, ModuleManager modules, ThemeManager themes, ClickGuiState state) {
        super(themes, state.panel(category.name()));
        this.category = category;
        for (Module module : modules.inCategory(category)) {
            this.entries.add(new ModuleEntry(module, themes, state));
        }
    }

    public void setFilter(String query) {
        this.filter = query == null ? Deobf.decrypt("") : query.toLowerCase(Locale.ROOT).trim();
    }

    private List<ModuleEntry> visibleEntries() {
        if (this.filter.isEmpty()) {
            return this.entries;
        }
        return this.entries.stream().filter(e -> e.getModule().getName().toLowerCase(Locale.ROOT).contains(this.filter)).toList();
    }

    @Override
    protected String title() {
        return this.category.getDisplayName();
    }

    @Override
    protected int icon() {
        return NVGIcons.get(this.category);
    }

    @Override
    protected float contentHeight(NVGRenderer vg) {
        float h = 12.0f;
        for (ModuleEntry entry : this.visibleEntries()) {
            h += entry.height(vg) + 3.0f;
        }
        return h - 3.0f;
    }

    @Override
    protected void renderContent(NVGRenderer vg, float startY, float mx, float my, float viewTop, float viewBottom) {
        float rowY = startY;
        float entryWidth = 198.0f;
        for (ModuleEntry entry : this.visibleEntries()) {
            float h = entry.height(vg);
            entry.setBounds(this.ps.x + 6.0f, rowY, entryWidth);
            if (rowY + h >= viewTop - 20.0f && rowY <= viewBottom + 20.0f) {
                float fade = this.edgeFade(rowY, rowY + h, viewTop, viewBottom);
                entry.render(vg, mx, my, fade);
            }
            rowY += h + 3.0f;
        }
    }

    @Override
    public boolean mouseClicked(float mx, float my, int button) {
        for (ModuleEntry entry : this.visibleEntries()) {
            if (!entry.mouseClicked(mx, my, button)) continue;
            return true;
        }
        return false;
    }

    @Override
    public void mouseDragged(float mx, float my) {
        for (ModuleEntry entry : this.visibleEntries()) {
            entry.mouseDragged(mx, my);
        }
    }

    @Override
    public void mouseReleased() {
        for (ModuleEntry entry : this.visibleEntries()) {
            entry.mouseReleased();
        }
    }

    @Override
    public boolean keyPressed(int keyCode) {
        for (ModuleEntry entry : this.visibleEntries()) {
            if (!entry.keyPressed(keyCode)) continue;
            return true;
        }
        return false;
    }

    @Override
    public boolean charTyped(int codepoint) {
        for (ModuleEntry entry : this.visibleEntries()) {
            if (!entry.charTyped(codepoint)) continue;
            return true;
        }
        return false;
    }

    @Override
    public boolean isListening() {
        for (ModuleEntry entry : this.visibleEntries()) {
            if (!entry.isListening()) continue;
            return true;
        }
        return false;
    }
}

