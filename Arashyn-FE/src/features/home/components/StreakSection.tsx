import {Flame} from "lucide-react";
import {ACHIEVEMENTS, users} from "@/features/home/constants/streak_constant.ts";


export default function StreakSection() {
    return (
        <section className="relative z-10 w-full py-32 mt-[-10vh]">
            <div className="container mx-auto px-6">
                <SectionHeading />

                <div className="grid gap-8 lg:grid-cols-[0.8fr_1fr_0.8fr]">
                    <StreakCard />

                    <div className="flex flex-col gap-4">
                        <WeeklyXP />
                        <Leaderboard />
                    </div>

                    <AchievementGrid />
                </div>
            </div>
        </section>
    );
}

function SectionHeading() {
    return (
        <div className="mb-16 text-center">
            <div className="border-indigo-500/20 bg-indigo-500/10 text-indigo-400 inline-flex items-center gap-2 rounded-full border px-4 py-2 text-xs font-semibold uppercase tracking-[0.2em]">
                <Flame className="size-3" />
                Streaks & Achievements
            </div>

            <h2 className="mt-6 text-5xl font-bold text-white">
                Grammar is a daily habit.
                <br />
                We make it one.
            </h2>
        </div>
    );
}

function StreakCard() {
    return (
        <div className="border-white/10 bg-white/[0.03] rounded-3xl border p-8 text-center
        backdrop-blur-xl
            ">
            <div className="mb-3 text-7xl">🔥</div>
            <div className="text-6xl font-extrabold text-white">47</div>
            <div className="mt-2 text-lg font-semibold text-white">Day Streak</div>
            <div className="mb-6 text-sm text-white/50">Top 8% of all learners</div>

            <div className="grid grid-cols-7 gap-1">
                {Array.from({ length: 28 }).map((_, i) => (
                    <div
                        key={i}
                        className={`aspect-square rounded-sm ${
                            i < 21
                                ? "bg-indigo-500"
                                : i < 25
                                    ? "bg-indigo-500/30"
                                    : "bg-white/5"
                        }`}
                    />
                ))}
            </div>
        </div>
    );
}

function WeeklyXP() {
    return (
        <div className="border-white/10 bg-white/[0.03] rounded-3xl border p-6 backdrop-blur-xl">
            <p className="mb-4 text-xs font-semibold text-white/50">WEEKLY XP</p>

            <div className="flex h-24 items-end gap-2">
                {[60, 80, 45, 90, 70, 100, 85].map((h, i) => (
                    <div
                        key={i}
                        className={`flex-1 rounded-t-sm ${
                            i === 6 ? "bg-indigo-500" : "bg-indigo-500/20"
                        }`}
                        style={{ height: `${h}%` }}
                    />
                ))}
            </div>

            <div className="mt-2 flex justify-between text-[10px] text-white/40">
                {["M", "T", "W", "T", "F", "S", "S"].map((d) => (
                    <span key={d}>{d}</span>
                ))}
            </div>
        </div>
    );
}

function Leaderboard() {
    return (
        <div className="border-white/10 bg-white/[0.03] rounded-3xl border p-6 backdrop-blur-xl">
            <p className="mb-4 text-xs font-semibold text-white/50">
                GLOBAL LEADERBOARD
            </p>

            {users.map((user) => (
                <div
                    key={user.rank}
                    className={`mb-1 flex items-center justify-between rounded-lg px-2 py-2 text-sm ${
                        user.active ? "bg-indigo-500/10" : ""
                    }`}
                >
                    <div className="flex items-center gap-3">
                        <span className="w-5 text-xs text-yellow-400">#{user.rank}</span>
                        <span className={user.active ? "text-indigo-400" : "text-white"}>
              {user.name}
            </span>
                    </div>
                    <span className="text-xs text-white/50">{user.xp} XP</span>
                </div>
            ))}
        </div>
    );
}

function AchievementGrid() {
    return (
        <div className="grid grid-cols-2 gap-4 h-[55vh]">
            {ACHIEVEMENTS.map((item) => {
                const Icon = item.icon;

                return (
                    <div
                        key={item.label}
                        className="border-white/10 bg-white/[0.03] rounded-3xl border
                        p-4 text-center backdrop-blur-xl"
                    >
                        <div className="mx-auto mb-3 flex size-10 items-center justify-center rounded-xl bg-white/5">
                            <Icon className={`size-5 ${item.color}`} />
                        </div>
                        <p className="text-xs font-medium text-white">{item.label}</p>
                    </div>
                );
            })}
        </div>
    );
}