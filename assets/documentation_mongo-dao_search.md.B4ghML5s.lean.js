import{_ as i,c as a,a2 as t,o as n}from"./chunks/framework.DPuwY6B9.js";const c=JSON.parse('{"title":"Search Functions","description":"","frontmatter":{},"headers":[],"relativePath":"documentation/mongo-dao/search.md","filePath":"documentation/mongo-dao/search.md","lastUpdated":1730840042000}'),h={name:"documentation/mongo-dao/search.md"};function e(l,s,p,k,r,o){return n(),a("div",null,s[0]||(s[0]=[t(`<h1 id="search-functions" tabindex="-1">Search Functions <a class="header-anchor" href="#search-functions" aria-label="Permalink to &quot;Search Functions&quot;">​</a></h1><h2 id="info" tabindex="-1">Info <a class="header-anchor" href="#info" aria-label="Permalink to &quot;Info&quot;">​</a></h2><p><a href="./">MongoDAO</a> Search Functions</p><h2 id="demo" tabindex="-1">Demo <a class="header-anchor" href="#demo" aria-label="Permalink to &quot;Demo&quot;">​</a></h2><h3 id="setup-imports" tabindex="-1">Setup imports <a class="header-anchor" href="#setup-imports" aria-label="Permalink to &quot;Setup imports&quot;">​</a></h3><div class="language-scala vp-adaptive-theme"><button title="Copy Code" class="copy"></button><span class="lang">scala</span><pre class="shiki shiki-themes github-light github-dark vp-code" tabindex="0"><code><span class="line"><span style="--shiki-light:#6A737D;--shiki-dark:#6A737D;">// Filter helper functions</span></span>
<span class="line"><span style="--shiki-light:#D73A49;--shiki-dark:#F97583;">import</span><span style="--shiki-light:#6F42C1;--shiki-dark:#B392F0;"> dev</span><span style="--shiki-light:#24292E;--shiki-dark:#E1E4E8;">.</span><span style="--shiki-light:#6F42C1;--shiki-dark:#B392F0;">mongocamp</span><span style="--shiki-light:#24292E;--shiki-dark:#E1E4E8;">.</span><span style="--shiki-light:#6F42C1;--shiki-dark:#B392F0;">driver</span><span style="--shiki-light:#24292E;--shiki-dark:#E1E4E8;">.</span><span style="--shiki-light:#6F42C1;--shiki-dark:#B392F0;">mongodb</span><span style="--shiki-light:#24292E;--shiki-dark:#E1E4E8;">.</span><span style="--shiki-light:#6F42C1;--shiki-dark:#B392F0;">Filter</span><span style="--shiki-light:#24292E;--shiki-dark:#E1E4E8;">.</span><span style="--shiki-light:#6F42C1;--shiki-dark:#B392F0;">_</span></span>
<span class="line"><span style="--shiki-light:#6A737D;--shiki-dark:#6A737D;">// sort helper functions</span></span>
<span class="line"><span style="--shiki-light:#D73A49;--shiki-dark:#F97583;">import</span><span style="--shiki-light:#6F42C1;--shiki-dark:#B392F0;"> dev</span><span style="--shiki-light:#24292E;--shiki-dark:#E1E4E8;">.</span><span style="--shiki-light:#6F42C1;--shiki-dark:#B392F0;">mongocamp</span><span style="--shiki-light:#24292E;--shiki-dark:#E1E4E8;">.</span><span style="--shiki-light:#6F42C1;--shiki-dark:#B392F0;">driver</span><span style="--shiki-light:#24292E;--shiki-dark:#E1E4E8;">.</span><span style="--shiki-light:#6F42C1;--shiki-dark:#B392F0;">mongodb</span><span style="--shiki-light:#24292E;--shiki-dark:#E1E4E8;">.</span><span style="--shiki-light:#6F42C1;--shiki-dark:#B392F0;">Sort</span><span style="--shiki-light:#24292E;--shiki-dark:#E1E4E8;">.</span><span style="--shiki-light:#6F42C1;--shiki-dark:#B392F0;">_</span></span>
<span class="line"><span style="--shiki-light:#6A737D;--shiki-dark:#6A737D;">// implicits like Document from Map ...</span></span>
<span class="line"><span style="--shiki-light:#D73A49;--shiki-dark:#F97583;">import</span><span style="--shiki-light:#6F42C1;--shiki-dark:#B392F0;"> dev</span><span style="--shiki-light:#24292E;--shiki-dark:#E1E4E8;">.</span><span style="--shiki-light:#6F42C1;--shiki-dark:#B392F0;">mongocamp</span><span style="--shiki-light:#24292E;--shiki-dark:#E1E4E8;">.</span><span style="--shiki-light:#6F42C1;--shiki-dark:#B392F0;">driver</span><span style="--shiki-light:#24292E;--shiki-dark:#E1E4E8;">.</span><span style="--shiki-light:#6F42C1;--shiki-dark:#B392F0;">mongodb</span><span style="--shiki-light:#24292E;--shiki-dark:#E1E4E8;">.</span><span style="--shiki-light:#6F42C1;--shiki-dark:#B392F0;">_</span></span></code></pre></div><h3 id="execute-search" tabindex="-1">Execute Search <a class="header-anchor" href="#execute-search" aria-label="Permalink to &quot;Execute Search&quot;">​</a></h3><div class="language-scala vp-adaptive-theme"><button title="Copy Code" class="copy"></button><span class="lang">scala</span><pre class="shiki shiki-themes github-light github-dark vp-code" tabindex="0"><code><span class="line"><span style="--shiki-light:#D73A49;--shiki-dark:#F97583;">val</span><span style="--shiki-light:#E36209;--shiki-dark:#FFAB70;"> females</span><span style="--shiki-light:#D73A49;--shiki-dark:#F97583;"> =</span><span style="--shiki-light:#6F42C1;--shiki-dark:#B392F0;"> PersonDAO</span><span style="--shiki-light:#24292E;--shiki-dark:#E1E4E8;">.find(</span><span style="--shiki-light:#6F42C1;--shiki-dark:#B392F0;">Map</span><span style="--shiki-light:#24292E;--shiki-dark:#E1E4E8;">(</span><span style="--shiki-light:#032F62;--shiki-dark:#9ECBFF;">&quot;gender&quot;</span><span style="--shiki-light:#D73A49;--shiki-dark:#F97583;"> -&gt;</span><span style="--shiki-light:#032F62;--shiki-dark:#9ECBFF;"> &quot;female&quot;</span><span style="--shiki-light:#24292E;--shiki-dark:#E1E4E8;">), sortByKey(</span><span style="--shiki-light:#032F62;--shiki-dark:#9ECBFF;">&quot;name&quot;</span><span style="--shiki-light:#24292E;--shiki-dark:#E1E4E8;">)).resultList()</span></span></code></pre></div>`,8)]))}const E=i(h,[["render",e]]);export{c as __pageData,E as default};
