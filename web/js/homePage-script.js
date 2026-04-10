const menusItemsDropdown = document.querySelectorAll(".menu-item-dropdown");
const menusItemsStatic = document.querySelectorAll(".menu-item-static");
const sidebar = document.getElementById("sidebar");
const sidebarBtn = document.getElementById("sidebar-btn");

sidebarBtn.addEventListener("click", () => {
    sidebar.classList.toggle("minimized");
});

menusItemsDropdown.forEach((menuItem) => {
    menuItem.addEventListener("click", () => {
        const subMenu = menuItem.querySelector(".sub-menu");
        const isActive = menuItem.classList.toggle("sub-menu-toggle");
        if (subMenu) {
            if (isActive) {
                subMenu.style.height = `${subMenu.scrollHeight + 6}px`;
                subMenu.style.padding = "0.2rem 0";
            } else {
                subMenu.style.height = "0";
                subMenu.style.padding = "0";
            }
        }
        menusItemsDropdown.forEach((item) => {
            if (item !== menuItem) {
                item.classList.remove("sub-menu-toggle");
                const otherSubMenu = item.querySelector(".sub-menu");
                if (otherSubMenu) {
                    item.classList.remove("sub-menu-toggle");
                    otherSubMenu.style.height = "0";
                    otherSubMenu.style.padding = "0";
                }
            }
        });
    });
});

menusItemsStatic.forEach((menuItem) => {
    menuItem.addEventListener("mouseenter", () => {
        if (sidebar.classList.contains("minimized")) {
            menusItemsDropdown.forEach((item) => {
                const otherSubMenu = item.querySelector(".sub-menu");
                if (otherSubMenu) {
                    item.classList.remove("sub-menu-toggle");
                    otherSubMenu.style.height = "0";
                    otherSubMenu.style.padding = "0";
                }
            });
        }
    });
});
