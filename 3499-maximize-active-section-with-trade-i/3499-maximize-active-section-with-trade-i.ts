function maxActiveSectionsAfterTrade(s: string): number {
    const t = "1" + s + "1";

    let ones = 0;
    for (const ch of s) {
        if (ch === '1') {
            ones++;
        }
    }

    let answer = ones;

    const blocks: { ch: string; len: number }[] = [];

    let i = 0;

    while (i < t.length) {
        let j = i;

        while (j < t.length && t[j] === t[i]) {
            j++;
        }

        blocks.push({
            ch: t[i],
            len: j - i
        });

        i = j;
    }

    for (let i = 1; i < blocks.length - 1; i++) {
        if (blocks[i].ch !== "1") {
            continue;
        }

        if (blocks[i - 1].ch === "0" && blocks[i + 1].ch === "0") {
            const gain = blocks[i - 1].len + blocks[i + 1].len;
            answer = Math.max(answer, ones + gain);
        }
    }

    return answer;
}