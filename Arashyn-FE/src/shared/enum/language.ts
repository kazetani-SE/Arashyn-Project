export const Language = {
    VI: "VI",
    EN: "EN",
    JA: "JA",
    KO: "KO",
    ZH: "ZH",
} as const;

export type Language = typeof Language[keyof typeof Language];