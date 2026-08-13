export async function enableMocking(): Promise<void> {
    const useMock = import.meta.env.VITE_USE_MOCK === "true";

    if (!useMock) {
        return;
    }

    const { worker } = await import("@/mocks/browser.ts");

    await worker.start({
        onUnhandledRequest: "bypass",
    });
}